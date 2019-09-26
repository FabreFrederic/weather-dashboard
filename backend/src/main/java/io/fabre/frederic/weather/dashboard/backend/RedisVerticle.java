package io.fabre.frederic.weather.dashboard.backend;

import io.fabre.frederic.weather.dashboard.backend.data.EventBusAddress;
import io.fabre.frederic.weather.dashboard.backend.data.Reading;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.Json;
import io.vertx.reactivex.redis.client.Redis;
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

    private static final String HOST = "127.0.0.1";
    private static final String PORT = "6379";

    @Override
    public void start() {
        LOGGER.info("This verticle is starting");

        createRedisClient(onCreate -> {
            if (onCreate.succeeded()) {
                LOGGER.info("redis OK");

                // For each new reading event bus addresses
                getNewReadingEventBusAddressesOnly().forEach(address -> {
                    vertx.eventBus().consumer(address.getValue(), message -> {
                        final Reading reading = Json.decodeValue(message.body().toString(), Reading.class);
                        final String minKey = getMinRedisKey(reading);
                        final String maxKey = getMaxRedisKey(reading);

                        redisClient.get(minKey, redisValue -> {
                            LOGGER.info("{} : Min value found : {}", address.getValue(), redisValue);

                            LOGGER.info("redisValue.result() : {}", redisValue.result());
                            LOGGER.info("reading.getValue() : {}", reading.getValue());

                            if (StringUtils.isBlank(redisValue.result()) ||
                                    Float.valueOf(reading.getValue()) < Float.valueOf(redisValue.result())) {
                                // Publish new extreme reading
                                publishExtremeReading(minKey + ".address", message);

                                // The new reading value is the new min value
                                storeNewExtremeReadingInRedis(reading, minKey, address);
                            }
                        });

                        redisClient.get(maxKey, redisValue -> {
                            LOGGER.info("{} : Max value found : {}", address.getValue(), redisValue);

                            if (StringUtils.isBlank(redisValue.result()) ||
                                    Float.valueOf(reading.getValue()) > Float.valueOf(redisValue.result())) {
                                // Publish new extreme reading
                                publishExtremeReading(maxKey + ".address", message);

                                // The new reading value is the new max value
                                storeNewExtremeReadingInRedis(reading, maxKey, address);
                            }
                        });

                    });
                });
            } else {
                LOGGER.error("Redis cannot be reached : {}", onCreate);
            }
        });
    }

    /**
     * Return Redis options with Docker config or default config the backend is not a container
     *
     * @return RedisOptions
     */
    private RedisOptions getRedisOptions() {
        String host = System.getenv("REDIS_HOST");
        String port = System.getenv("REDIS_PORT");

        if (StringUtils.isBlank(host)) {
            // No Docker environment
            host = HOST;
        }
        if (StringUtils.isBlank(port)) {
            // No Docker environment
            port = PORT;
        }
        LOGGER.info("Redis host : {}", host);
        LOGGER.info("Redis port : {}", port);

        return new RedisOptions().setAddress(host).setPort(Integer.parseInt(port));
    }

    /**
     * Will create a redis client and setup a reconnect handler when there is
     * an exception in the connection.
     */
    private void createRedisClient(Handler<AsyncResult<Redis>> handler) {
        redisClient = RedisClient.create(vertx, getRedisOptions());
        redisClient.ping(ar -> {
                .connect(onConnect -> {
                    if (onConnect.succeeded()) {
                        redisClient = onConnect.result();
                        // make sure the client is reconnected on error
                        redisClient.exceptionHandler(e -> {
                            attemptReconnect();
                        });
                    }
                    // allow further processing
                    handler.handle(onConnect);
                });
    }

    /**
     * Attempt to reconnect up to MAX_RECONNECT_RETRIES
     */
    private void attemptReconnect() {
        vertx.setTimer(2000, timer -> createRedisClient(onReconnect -> {
            if (onReconnect.failed()) {
                attemptReconnect();
            }
        }));
    }

    private List<EventBusAddress> getNewReadingEventBusAddressesOnly() {
        List<EventBusAddress> addresses = Arrays.asList(EventBusAddress.values());

        return addresses.stream().
                filter(address -> StringUtils.containsIgnoreCase(address.getValue(), "new")).
                collect(Collectors.toList());
    }

    private void publishExtremeReading(final String address, final Message message) {
        LOGGER.info("New extreme reading value published : {}", message);
        vertx.eventBus().publish(address, message.body().toString());
    }

    private String buildRedisKey(final Reading reading) {
        return reading.getSensorEnvironment().toString().toLowerCase() +
                "." +
                reading.getSensorType().toString().toLowerCase();
    }

    private String getMinRedisKey(final Reading reading) {
        return buildRedisKey(reading) + ".min";
    }

    private String getMaxRedisKey(final Reading reading) {
        return buildRedisKey(reading) + ".max";
    }

    private void storeNewExtremeReadingInRedis(final Reading reading,
                                               final String extremeKey,
                                               final EventBusAddress address) {
        redisClient.set(extremeKey, reading.getValue(), result -> {
            if (result.succeeded()) {
                LOGGER.info("{} : extreme value - Key stored : {}",
                        address.getValue(), reading.getValue());
            } else {
                LOGGER.info("Connection or operation Failed {0}", result.cause());
            }
        });
    }
}
