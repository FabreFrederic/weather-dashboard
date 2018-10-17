package io.fabre.frederic.weather.dashboard.backend.proxy;


import io.fabre.frederic.weather.dashboard.backend.data.Temperature;
import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.ProxyClose;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.reactivex.ext.mongo.MongoClient;

import java.util.List;

@ProxyGen
public interface MyService {

    @GenIgnore
    static MyService create(final MongoClient mongoClient, final Handler<AsyncResult<MyService>> readyHandler) {
        return new MyServiceImpl(mongoClient, readyHandler);
    }

    @GenIgnore
    static MyService createProxy(Vertx vertx, String address) {
        return new MyServiceVertxEBProxy(vertx, address);
    }

    @Fluent
    MyService sayHello(String name, Handler<AsyncResult<String>> handler);

    @Fluent
    MyService findAll(Handler<AsyncResult<List<Temperature>>> resultHandler);

    @ProxyClose
    public void close();

//    static MyService createProxy(Vertx vertx, String address) {
//        return new MyServiceVertxEBProxy(vertx, address);
//    }
//
//    static MyService create(MongoClient client) {
//        return new MyServiceImpl(client);
//    }


}
