package io.fabre.frederic.weather.dashboard.backend.data;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.reactivex.ext.mongo.MongoClient;

import java.util.List;

@ProxyGen
public interface TemperatureRepository {

    @Fluent
    TemperatureRepository save(final Reading reading, final Handler<AsyncResult<Reading>> resultHandler);

    @Fluent
    TemperatureRepository findTodayReadings(final SensorEnvironment sensorEnvironment,
                                            final SensorType sensorType,
                                            final Handler<AsyncResult<List<Reading>>> resultHandler);

    @Fluent
    TemperatureRepository findTodayLastReading(final SensorEnvironment sensorEnvironment,
                                               final SensorType sensorType,
                                               final Handler<AsyncResult<Reading>> resultHandler);

    @Fluent
    TemperatureRepository findTodayMaxReading(final SensorEnvironment sensorEnvironment,
                                              final SensorType sensorType,
                                              final Handler<AsyncResult<Reading>> resultHandler);

    @Fluent
    TemperatureRepository findTodayMinReading(final SensorEnvironment sensorEnvironment,
                                              final SensorType sensorType,
                                              final Handler<AsyncResult<Reading>> resultHandler);

    static TemperatureRepository createProxy(final Vertx vertx, final String address) {
        return new TemperatureRepositoryVertxEBProxy(vertx, address);
    }

    static TemperatureRepository create(final MongoClient client) {
        return new TemperatureRepositoryImpl(client);
    }
}
