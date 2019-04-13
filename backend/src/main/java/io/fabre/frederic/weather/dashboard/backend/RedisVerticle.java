package io.fabre.frederic.weather.dashboard.backend;

import io.fabre.frederic.weather.dashboard.backend.data.EventBusAddresses;
import io.fabre.frederic.weather.dashboard.backend.data.Reading;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.Json;
import io.vertx.redis.RedisClient;
import io.vertx.redis.RedisOptions;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RedisVerticle extends AbstractVerticle {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisVerticle.class);
    private RedisClient redisClient;

    @Override
    public void start() {
        LOGGER.info("This verticle is starting");
        redisClient =
                RedisClient.create(Vertx.vertx(), new RedisOptions().setAddress("127.0.0.1").setPort(6379).toJson());
        redisClient.ping(ar -> {
            if (ar.succeeded()) {
                LOGGER.info("redis OK");

                getNewReadingEventBusAddressesOnly().forEach(address -> {
                    vertx.eventBus().consumer(address.getValue(), message -> {
                        final Reading reading = Json.decodeValue(message.body().toString(), Reading.class);
                        final String minKey = reading.getSensorEnvironment().toString().toLowerCase() +
                                "." +
                                reading.getSensorType().toString().toLowerCase() +
                                ".min";
                        final String maxKey = reading.getSensorEnvironment().toString().toLowerCase() +
                                "." +
                                reading.getSensorType().toString().toLowerCase() +
                                ".max";

                        redisClient.get(minKey, redisValue -> {
                            LOGGER.info("{} : Min value found : {}", address.getValue(), redisValue);

                            if (StringUtils.isBlank(redisValue.result()) ||
                                    Float.valueOf(reading.getValue()) < Float.valueOf(redisValue.result())) {
                                // Publish new extrem reading
                                publishExtremReading(minKey + ".address", message);

                                // The new reading value is the new min value
                                redisClient.set(minKey, reading.getValue(), result -> {
                                    if (result.succeeded()) {
                                        LOGGER.info("{} : min value - Key stored : {}", address.getValue(), reading.getValue());
                                    } else {
                                        LOGGER.info("Connection or Operation Failed {}", result.cause());
                                    }
                                });
                            }
                        });

                        redisClient.get(maxKey, redisValue -> {
                            LOGGER.info("Max value found : {}", redisValue);

                            if (StringUtils.isBlank(redisValue.result()) ||
                                    Float.valueOf(reading.getValue()) > Float.valueOf(redisValue.result())) {
                                // Publish new extrem reading
                                publishExtremReading(maxKey + ".address", message);

                                // The new reading value is the new max value
                                redisClient.set(maxKey, reading.getValue(), result -> {
                                    if (result.succeeded()) {
                                        LOGGER.info("{} : max value - Key stored : {}", address.getValue(), reading.getValue());
                                    } else {
                                        LOGGER.info("Connection or Operation Failed {}", result.cause());
                                    }
                                });
                            }
                        });

                    });
                });
            } else {
                LOGGER.info("redis KO");
            }
        });


    }

    private List<EventBusAddresses> getNewReadingEventBusAddressesOnly() {
        List<EventBusAddresses> addresses = Arrays.asList(EventBusAddresses.values());

        return addresses.stream().
                filter(address -> StringUtils.containsIgnoreCase(address.getValue(), "new")).
                collect(Collectors.toList());
    }

    private void publishExtremReading(final String address, final Message message) {
        LOGGER.info("New extrem reading value published : {}", message.toString());
        vertx.eventBus().publish(address, message.body().toString());
    }

}
