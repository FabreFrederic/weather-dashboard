package io.fabre.frederic.weather.dashboard.backend;

import io.fabre.frederic.weather.dashboard.backend.data.EventBusAddress;
import io.fabre.frederic.weather.dashboard.backend.data.Reading;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.Json;
import io.vertx.core.net.SocketAddress;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisAPI;
import io.vertx.redis.client.RedisOptions;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RedisVerticle extends AbstractVerticle {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisVerticle.class);
    private Redis client;
    private RedisAPI redisAPI;

    private static final String DEFAULT_HOST = "127.0.0.1";
    private static final String DEFAULT_PORT = "6379";
    private static final long RECONNECTION_DELAY_MS = 5000;
    private RedisOptions redisOptions;

    @Override
    public void start() {
        LOGGER.info("This verticle is starting");
        redisOptions = getRedisOptions();

        createRedisClient(onCreate -> {
            if (onCreate.succeeded()) {
                client = onCreate.result();
                redisAPI = RedisAPI.api(client);

                // For each new reading event bus addresses
                getNewReadingEventBusAddressesOnly().forEach(address -> {
                    vertx.eventBus().consumer(address.getValue(), message -> {
                        final Reading reading = Json.decodeValue(message.body().toString(), Reading.class);
                        final String minKey = buildMinRedisKeyName(reading);
                        final String maxKey = buildMaxRedisKeyName(reading);

                        final Future<Optional<String>> minCache = getCache(minKey);
                        LOGGER.info("{} : Min value found: {}", address.getValue(), minCache.result());
                        minCache.setHandler(ar -> {
                            if (ar.succeeded()) {
                                if (!ar.result().isPresent() ||
                                        Float.valueOf(reading.getValue()) < Float.valueOf(ar.result().get())) {
                                    publishExtremeReadingOnEventBus(minKey + ".address", message);

                                    // The new reading value is the new min value, and has to be stored in cache
                                    LOGGER.info("{} : New min value stored in cache: {}",
                                            address.getValue(), reading.getValue());
                                    setCache(minKey, reading);
                                }
                            }
                        });

                        final Future<Optional<String>> maxCache = getCache(maxKey);
                        LOGGER.info("{} : Max value found: {}", address.getValue(), maxCache.result());
                        maxCache.setHandler(ar -> {
                            if (ar.succeeded()) {
                                if (!ar.result().isPresent() ||
                                        Float.valueOf(reading.getValue()) > Float.valueOf(ar.result().get())) {
                                    publishExtremeReadingOnEventBus(maxKey + ".address", message);

                                    // The new reading value is the new max value, and has to be stored in cache
                                    LOGGER.info("{} : New max value stored in cache: {}",
                                            address.getValue(), reading.getValue());
                                    setCache(maxKey, reading);
                                }
                            }
                        });
                    });
                });
            } else {
                attemptReconnect();
            }
        });
    }

    /**
     * Return Redis options with Docker config or default config if the backend is not a container
     *
     * @return RedisOptions
     */
    private RedisOptions getRedisOptions() {
        String host = System.getenv("REDIS_HOST");
        String port = System.getenv("REDIS_PORT");

        if (StringUtils.isBlank(host)) {
            // No Docker environment
            host = DEFAULT_HOST;
        }
        if (StringUtils.isBlank(port)) {
            // No Docker environment
            port = DEFAULT_PORT;
        }
        LOGGER.info("Redis host : {}", host);
        LOGGER.info("Redis port : {}", port);

        return new RedisOptions().addEndpoint(SocketAddress.inetSocketAddress(Integer.parseInt(port), host));
    }

    /**
     * Will create a redis client and setup a reconnect handler when there is
     * an exception in the connection.
     */
    private void createRedisClient(Handler<AsyncResult<Redis>> handler) {
        Redis.createClient(vertx, redisOptions).connect(onConnect -> {
            if (onConnect.succeeded()) {
                client = onConnect.result();
                client.exceptionHandler(e -> attemptReconnect());
                LOGGER.info("Connected to Redis");
            }
            handler.handle(onConnect);
        });
    }

    /**
     * Attempt to reconnect
     */
    private void attemptReconnect() {
        vertx.setTimer(RECONNECTION_DELAY_MS,
                timer -> createRedisClient(onReconnect -> {
                    if (onReconnect.failed()) {
                        LOGGER.warn("Error - Redis connection failed, trying to reconnect");
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

    private void publishExtremeReadingOnEventBus(final String address, final Message message) {
        LOGGER.info("New extreme reading value published : {}", message);
        vertx.eventBus().publish(address, message.body().toString());
    }

    private String buildRedisKeyName(final Reading reading) {
        return reading.getSensorEnvironment().toString().toLowerCase() +
                "." +
                reading.getSensorType().toString().toLowerCase();
    }

    private String buildMinRedisKeyName(final Reading reading) {
        return buildRedisKeyName(reading) + ".min";
    }

    private String buildMaxRedisKeyName(final Reading reading) {
        return buildRedisKeyName(reading) + ".max";
    }

    private Future<Void> setCache(final String key, final Reading reading) {
        Promise<Void> promise = Promise.promise();
        redisAPI.set(Arrays.asList(key, reading.getValue()), result -> {
            if (result.succeeded()) {
                promise.complete();
                LOGGER.info("Operation succeeded {0}", result.cause());
            } else {
                promise.fail(result.cause());
            }
        });
        return promise.future();
    }

    private Future<Optional<String>> getCache(String keyName) {
        Promise<Optional<String>> promise = Promise.promise();
        redisAPI.get(keyName, handler -> {
            if (handler.succeeded()) {
                if (handler.result() != null) {
                    promise.complete(Optional.ofNullable(handler.result().toString()));
                } else {
                    promise.complete(Optional.empty());
                }
            } else {
                promise.fail(handler.cause());
            }
        });
        return promise.future();
    }
}