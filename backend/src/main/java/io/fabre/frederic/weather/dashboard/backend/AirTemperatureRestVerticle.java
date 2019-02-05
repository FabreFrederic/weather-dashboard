package io.fabre.frederic.weather.dashboard.backend;

import io.fabre.frederic.weather.dashboard.backend.data.Reading;
import io.fabre.frederic.weather.dashboard.backend.data.SensorEnvironment;
import io.fabre.frederic.weather.dashboard.backend.data.SensorType;
import io.fabre.frederic.weather.dashboard.backend.data.TemperatureRepository;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AirTemperatureRestVerticle extends AbstractVerticle {
    private static final Logger LOGGER = LoggerFactory.getLogger(AirTemperatureRestVerticle.class);
    private TemperatureRepository repository;
    private static final String TEMPERATURE_SERVICE = "mongo.service";

    @Override
    public void start() {
        LOGGER.info("This verticle is starting");

        Router router = Router.router(vertx);
        router.route().handler(getCorsHandler());
        router.route().handler(BodyHandler.create());
        router.get("/air/temperature/today").produces("application/json").handler(this::getTodayAirTemperatureHandler);
        router.get("/water/temperature/today").produces("application/json").handler(this::getTodayWaterTemperatureHandler);

        vertx.createHttpServer().requestHandler(router::accept).listen(8085);
        repository = TemperatureRepository.createProxy(vertx, TEMPERATURE_SERVICE);
    }

    private CorsHandler getCorsHandler() {
        CorsHandler corsHandler = CorsHandler.create("*");
        corsHandler.allowedMethod(HttpMethod.GET);
        corsHandler.allowedMethod(HttpMethod.OPTIONS);
        corsHandler.allowedHeader("Authorization");
        corsHandler.allowedHeader("Content-Type");
        corsHandler.allowedHeader("Set-Cookie");
        corsHandler.allowedHeader("Access-Control-Allow-Origin");
        corsHandler.allowedHeader("Access-Control-Allow-Headers");
        return corsHandler;
    }

    private void getTodayAirTemperatureHandler(final RoutingContext routingContext) {
        repository.findTodayReadings(SensorEnvironment.AIR, SensorType.TEMPERATURE, res -> {
            JsonArray jsonArray = new JsonArray();
            List<Reading> readings = res.result();
            readings.forEach(temperature -> jsonArray.add(JsonObject.mapFrom(temperature)));
            HttpServerResponse response = routingContext.response();
            response.putHeader("content-type", "application/json");
            LOGGER.info(jsonArray.toString());
            response.end(jsonArray.toString());
        });
    }

    private void getTodayWaterTemperatureHandler(final RoutingContext routingContext) {
        repository.findTodayReadings(SensorEnvironment.WATER, SensorType.TEMPERATURE, res -> {
            JsonArray jsonArray = new JsonArray();
            List<Reading> readings = res.result();
            readings.forEach(temperature -> jsonArray.add(JsonObject.mapFrom(temperature)));
            HttpServerResponse response = routingContext.response();
            response.putHeader("content-type", "application/json");
            LOGGER.info(jsonArray.toString());
            response.end(jsonArray.toString());
        });
    }

}