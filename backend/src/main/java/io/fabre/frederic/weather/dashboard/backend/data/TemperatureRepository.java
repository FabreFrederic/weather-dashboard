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
    TemperatureRepository save(Temperature temperature, Handler<AsyncResult<Temperature>> resultHandler);

    @Fluent
    TemperatureRepository findTodayTemperature(Handler<AsyncResult<List<Temperature>>> resultHandler);

    static TemperatureRepository createProxy(Vertx vertx, String address) {
        return new TemperatureRepositoryVertxEBProxy(vertx, address);
    }

    static TemperatureRepository create(MongoClient client) {
        return new TemperatureRepositoryImpl(client);
    }
}
