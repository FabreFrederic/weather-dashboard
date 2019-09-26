package io.fabre.frederic.weather.dashboard.backend.repository;

import io.fabre.frederic.weather.dashboard.backend.data.Reading;
import io.fabre.frederic.weather.dashboard.backend.data.SensorEnvironment;
import io.fabre.frederic.weather.dashboard.backend.data.SensorType;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.redis.RedisClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RedisRepositoryImpl implements RedisRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisRepositoryImpl.class);
    private RedisClient redisClient;

    public RedisRepositoryImpl(final RedisClient redisClient) {
        this.redisClient = redisClient;
    }

    @Override
    public RedisRepository findTodayMaxReading(final SensorEnvironment sensorEnvironment,
                                               final SensorType sensorType,
                                               final Handler<AsyncResult<Reading>> resultHandler) {
        LOGGER.info("findTodayMaxReading from redis");

        return this;
    }
}
