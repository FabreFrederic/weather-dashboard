package io.fabre.frederic.weather.dashboard.backend;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.ext.bridge.PermittedOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.handler.sockjs.BridgeOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;

public class ServerVerticle extends AbstractVerticle {
    private static final String WATER_TEMPERATURE_ADDRESS = "water.temperature.address";
    private static final String AIR_TEMPERATURE_ADDRESS = "air.temperature.address";
    private static final String WATER_TEMPERATURE_FRONTEND_ADDRESS = "water.temperature.frontend.address";
    private static final String AIR_TEMPERATURE_FRONTEND_ADDRESS = "air.temperature.frontend.address";

    public void start(Future<Void> future) {
        Router router = Router.router(vertx);
        BridgeOptions opts = new BridgeOptions()
                .addInboundPermitted(new PermittedOptions().setAddress(WATER_TEMPERATURE_ADDRESS))
                .addInboundPermitted(new PermittedOptions().setAddress(AIR_TEMPERATURE_ADDRESS))
                .addOutboundPermitted(new PermittedOptions().setAddress(WATER_TEMPERATURE_FRONTEND_ADDRESS))
                .addOutboundPermitted(new PermittedOptions().setAddress(AIR_TEMPERATURE_FRONTEND_ADDRESS));

        SockJSHandler ebHandler = SockJSHandler.create(vertx).bridge(opts);

        router.route("/eventbus/*").handler(ebHandler);
        router.route().handler(StaticHandler.create());
        vertx.createHttpServer().requestHandler(router::accept).listen(8080);

        router.route().handler(io.vertx.ext.web.handler.CorsHandler.create("http://localhost:8080")
                .allowedMethod(io.vertx.core.http.HttpMethod.GET)
                .allowedMethod(io.vertx.core.http.HttpMethod.POST)
                .allowedMethod(io.vertx.core.http.HttpMethod.PUT)
                .allowedMethod(io.vertx.core.http.HttpMethod.OPTIONS)
                .allowCredentials(true)
                .allowedHeader("Access-Control-Allow-Method")
                .allowedHeader("Access-Control-Allow-Origin")
                .allowedHeader("Access-Control-Allow-Credentials")
                .allowedHeader("Content-Type"));
    }

}