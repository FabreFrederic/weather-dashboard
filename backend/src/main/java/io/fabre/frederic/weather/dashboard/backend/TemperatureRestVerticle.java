package io.fabre.frederic.weather.dashboard.backend;

import io.fabre.frederic.weather.dashboard.backend.data.Reading;
import io.fabre.frederic.weather.dashboard.backend.data.SensorEnvironment;
import io.fabre.frederic.weather.dashboard.backend.data.SensorType;
import io.fabre.frederic.weather.dashboard.backend.data.TemperatureRepository;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
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

public class TemperatureRestVerticle extends AbstractVerticle {
    private static final Logger LOGGER = LoggerFactory.getLogger(TemperatureRestVerticle.class);
    private TemperatureRepository repository;
    private static final String TEMPERATURE_SERVICE = "mongo.service";

    @Override
    public void start() {
        LOGGER.info("This verticle is starting");

        Router router = Router.router(vertx);
        router.route().handler(getCorsHandler());
        router.route().handler(BodyHandler.create());

        router.get("/air/temperatures/today").produces("application/json").
                handler(this::getTodayAirTemperatureHandler);
        router.get("/water/temperatures/today").produces("application/json").
                handler(this::getTodayWaterTemperatureHandler);

        router.get("/air/temperature/today/last").produces("application/json").
                handler(this::getTodayLastAirTemperatureHandler);
        router.get("/water/temperature/today/last").produces("application/json").
                handler(this::getTodayLastWaterTemperatureHandler);

        router.get("/water/temperature/today/max").produces("application/json").
                handler(this::getTodayMaxWaterTemperatureHandler);
        router.get("/water/temperature/today/min").produces("application/json").
                handler(this::getTodayMinWaterTemperatureHandler);

        router.get("/air/temperature/today/max").produces("application/json").
                handler(this::getTodayMaxAirTemperatureHandler);
        router.get("/air/temperature/today/min").produces("application/json").
                handler(this::getTodayMinAirTemperatureHandler);

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
        repository.findTodayReadings(SensorEnvironment.AIR, SensorType.TEMPERATURE, res ->
                handleMultipleReadingsResponse(routingContext, res));
    }

    private void getTodayWaterTemperatureHandler(final RoutingContext routingContext) {
        repository.findTodayReadings(SensorEnvironment.WATER, SensorType.TEMPERATURE, res ->
                handleMultipleReadingsResponse(routingContext, res));
    }

    private void getTodayLastAirTemperatureHandler(final RoutingContext routingContext) {
        repository.findTodayLastReading(SensorEnvironment.AIR, SensorType.TEMPERATURE, res ->
                handleSingleReadingResponse(routingContext, res));
    }

    private void getTodayLastWaterTemperatureHandler(final RoutingContext routingContext) {
        repository.findTodayLastReading(SensorEnvironment.WATER, SensorType.TEMPERATURE, res ->
                handleSingleReadingResponse(routingContext, res));
    }

    private void getTodayMinAirTemperatureHandler(final RoutingContext routingContext) {
        repository.findTodayMinReading(SensorEnvironment.AIR, SensorType.TEMPERATURE, res ->
                handleSingleReadingResponse(routingContext, res));
    }

    private void getTodayMaxAirTemperatureHandler(final RoutingContext routingContext) {
        repository.findTodayMaxReading(SensorEnvironment.AIR, SensorType.TEMPERATURE, res ->
                handleSingleReadingResponse(routingContext, res));
    }

    private void getTodayMinWaterTemperatureHandler(final RoutingContext routingContext) {
        repository.findTodayMinReading(SensorEnvironment.WATER, SensorType.TEMPERATURE, res ->
                handleSingleReadingResponse(routingContext, res));
    }

    private void getTodayMaxWaterTemperatureHandler(final RoutingContext routingContext) {
        repository.findTodayMaxReading(SensorEnvironment.WATER, SensorType.TEMPERATURE, res ->
                handleSingleReadingResponse(routingContext, res));
    }

    private void handleMultipleReadingsResponse(final RoutingContext routingContext,
                                                final AsyncResult<List<Reading>> resultHandler) {
        final JsonArray jsonArray = new JsonArray();
        final List<Reading> readings = resultHandler.result();
        readings.forEach(temperature -> jsonArray.add(JsonObject.mapFrom(temperature)));
        final HttpServerResponse response = routingContext.response();
        response.putHeader("content-type", "application/json");
        LOGGER.info(jsonArray.toString());
        response.end(jsonArray.toString());
    }

    private void handleSingleReadingResponse(final RoutingContext routingContext,
                                             final AsyncResult<Reading> resultHandler) {
        final JsonArray jsonArray = new JsonArray();
        final Reading reading = resultHandler.result();
        jsonArray.add(JsonObject.mapFrom(reading));
        final HttpServerResponse response = routingContext.response();
        response.putHeader("content-type", "application/json");
        LOGGER.info(jsonArray.toString());
        response.end(jsonArray.toString());
    }
}