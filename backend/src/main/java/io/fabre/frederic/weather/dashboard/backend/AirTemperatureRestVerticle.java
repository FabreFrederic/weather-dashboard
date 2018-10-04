package io.fabre.frederic.weather.dashboard.backend;

import io.fabre.frederic.weather.dashboard.backend.data.Temperature;
import io.fabre.frederic.weather.dashboard.backend.data.TemperatureRepository;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AirTemperatureRestVerticle extends AbstractVerticle {

    private static final Logger LOGGER = LoggerFactory.getLogger(AirTemperatureRestVerticle.class);
    private TemperatureRepository repository;
    private static final String TEMPERATURE_SERVICE = "temperature-service";
    private static final String AIR_TEMPERATURE_ADRESS = "air.temperature";
    private static final String WATER_TEMPERATURE_ADRESS = "water.temperature";

    @Override
    public void start() throws Exception {
        LOGGER.info("This verticle is starting");

        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());
        router.post("/air/temperature").produces("application/json").handler(this::saveHandler);
        router.post("/air/temperature").produces("application/json").handler(this::sendDataToEventBusHandler);

        router.post("/water/temperature").produces("application/json").handler(this::saveHandler);
        router.post("/water/temperature").produces("application/json").handler(this::sendDataToEventBusHandler);

        vertx.createHttpServer().requestHandler(router::accept).listen(8061);
        repository = TemperatureRepository.createProxy(vertx, TEMPERATURE_SERVICE);
    }

    private void saveHandler(final RoutingContext routingContext) {
        Temperature temperature = Json.decodeValue(routingContext.getBodyAsString(), Temperature.class);

        repository.save(temperature, res -> {
            Temperature temperatureResult = res.result();
            LOGGER.info("Saved : {}", temperatureResult.toString());
            routingContext.next();
        });
    }

    private void sendDataToEventBusHandler(final RoutingContext routingContext) {
        Temperature temperature = Json.decodeValue(routingContext.getBodyAsString(), Temperature.class);
        LOGGER.info("New temperature send to eventbus : {}", temperature.toString());

        vertx.eventBus().send(AIR_TEMPERATURE_ADRESS, temperature, reply ->  {
            LOGGER.info("New temperature consumed from eventbus : {}", temperature.toString());
            routingContext.response().end(temperature.toString());
        });

    }

}