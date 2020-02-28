package io.fabre.frederic.weather.dashboard.backend.repository;

import io.fabre.frederic.weather.dashboard.backend.data.Reading;
import io.fabre.frederic.weather.dashboard.backend.data.SensorEnvironment;
import io.fabre.frederic.weather.dashboard.backend.data.SensorType;
import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

@ProxyGen
public interface RedisRepository {

    @Fluent
    RedisRepository findTodayMaxReading(final SensorEnvironment sensorEnvironment,
                                              final SensorType sensorType,
                                              final Handler<AsyncResult<Reading>> resultHandler);
}