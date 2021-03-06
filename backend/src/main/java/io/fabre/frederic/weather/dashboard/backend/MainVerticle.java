package io.fabre.frederic.weather.dashboard.backend;

import io.vertx.core.AbstractVerticle;
import io.vertx.reactivex.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainVerticle extends AbstractVerticle {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainVerticle.class);

    @Override
    public void start() {
        LOGGER.info("Main verticle is starting");
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(MongoVerticle.class.getName());
        vertx.deployVerticle(SaveReadingMongodbVerticle.class.getName());

        vertx.deployVerticle(RedisVerticle.class.getName());
        vertx.deployVerticle(ServerVerticle.class.getName());
        vertx.deployVerticle(WaterTemperatureVerticle.class.getName());
        vertx.deployVerticle(AirTemperatureVerticle.class.getName());
        vertx.deployVerticle(TemperatureRestVerticle.class.getName());
    }
}
