package io.fabre.frederic.weather.dashboard.backend.proxy;

import io.fabre.frederic.weather.dashboard.backend.data.Temperature;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.reactivex.ext.mongo.MongoClient;

import java.util.List;

public class MyServiceImpl implements MyService {

    private MongoClient mongoClient;

    public MyServiceImpl(final MongoClient mongoClient, final Handler<AsyncResult<MyService>> readyHandler) {
        this.mongoClient = mongoClient;
        mongoClient.rxGetCollections().subscribe(resp -> {
            readyHandler.handle(Future.succeededFuture(this));
        }, cause -> {
            readyHandler.handle(Future.failedFuture(cause));
        });
    }

    @Override
    public MyService sayHello(String name, Handler<AsyncResult<String>> handler) {
        handler.handle(Future.succeededFuture("Hello " + name + "!"));
        return this;
    }

    @Override
    public MyService findAll(Handler<AsyncResult<List<Temperature>>> resultHandler) {
        return null;
    }

    @Override
    public void close() {
        mongoClient.close();
    }
}
