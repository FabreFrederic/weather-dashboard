package io.fabre.frederic.weather.dashboard.backend;

import io.fabre.frederic.weather.dashboard.backend.data.EventBusAddress;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.ext.bridge.PermittedOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.handler.sockjs.BridgeOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;

public class ServerVerticle extends AbstractVerticle {

    public void start(Future<Void> future) {
        Router router = Router.router(vertx);
        BridgeOptions opts = new BridgeOptions()
                .addInboundPermitted(
                        new PermittedOptions().setAddress(EventBusAddress.AIR_TEMPERATURE_RAW_ADDRESS.getValue()))
                .addInboundPermitted(
                        new PermittedOptions().setAddress(EventBusAddress.WATER_TEMPERATURE_RAW_ADDRESS.getValue()))
                .addOutboundPermitted(
                        new PermittedOptions().setAddress(EventBusAddress.AIR_TEMPERATURE_NEW_ADDRESS.getValue()))
                .addOutboundPermitted(
                        new PermittedOptions().setAddress(EventBusAddress.WATER_TEMPERATURE_NEW_ADDRESS.getValue()))
                .addOutboundPermitted(
                        new PermittedOptions().setAddress(EventBusAddress.AIR_TEMPERATURE_MIN_ADDRESS.getValue()))
                .addOutboundPermitted(
                        new PermittedOptions().setAddress(EventBusAddress.AIR_TEMPERATURE_MAX_ADDRESS.getValue()))
                .addOutboundPermitted(
                        new PermittedOptions().setAddress(EventBusAddress.WATER_TEMPERATURE_MIN_ADDRESS.getValue()))
                .addOutboundPermitted(
                        new PermittedOptions().setAddress(EventBusAddress.WATER_TEMPERATURE_MAX_ADDRESS.getValue()));

        SockJSHandler ebHandler = SockJSHandler.create(vertx).bridge(opts);

        router.route("/eventbus/*").handler(ebHandler);
        router.route().handler(StaticHandler.create());
        vertx.createHttpServer().requestHandler(router).listen(8082);

        router.route().handler(CorsHandler.create("http://localhost:8082")
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