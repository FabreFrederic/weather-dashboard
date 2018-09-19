package io.fabre.frederic.weather.dashboard.backend;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainVerticle extends AbstractVerticle {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainVerticle.class);

    @Override
    public void start() {
        LOGGER.info("Main verticle is starting");
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(PingVerticle.class.getName());
        vertx.deployVerticle(MongoVerticle.class.getName());
        vertx.deployVerticle(ServerVerticle.class.getName());
        vertx.deployVerticle(WaterTemperatureVerticle.class.getName());
        vertx.deployVerticle(AirTemperatureVerticle.class.getName());
    }
}
