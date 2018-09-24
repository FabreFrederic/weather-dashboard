package io.fabre.frederic.weather.dashboard.backend;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PingVerticle extends AbstractVerticle {
    private static final Logger LOGGER = LoggerFactory.getLogger(PingVerticle.class);

    @Override
    public void start() {
        LOGGER.info("This verticle is starting");
        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());
        router.get("/ping").handler(this::handleDefault);
        vertx.createHttpServer().requestHandler(router::accept).listen(8060);
    }

    private void handleDefault(RoutingContext routingContext) {
        HttpServerResponse response = routingContext.response();
        response.putHeader("content-type", "text/plain").end("hello");
        LOGGER.info("ping");
    }
}
