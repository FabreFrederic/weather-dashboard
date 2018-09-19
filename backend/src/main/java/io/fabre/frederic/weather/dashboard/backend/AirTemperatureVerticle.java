package io.fabre.frederic.weather.dashboard.backend;

import io.fabre.frederic.weather.dashboard.backend.data.Temperature;
import io.fabre.frederic.weather.dashboard.backend.data.TemperatureRepository;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AirTemperatureVerticle extends AbstractVerticle {

    private static final Logger LOGGER = LoggerFactory.getLogger(AirTemperatureVerticle.class);
    private static final String AIR_TEMPERATURE_ADDRESS = "air.temperature.address";
    private static final String MONGO_SERVICE = "mongo.service";
    private static final String AIR_TEMPERATURE_FRONTEND_ADDRESS = "air.temperature.frontend.address";

    @Override
    public void start() throws Exception {
        LOGGER.info("This verticle is starting");
        final TemperatureRepository repository = TemperatureRepository.createProxy(vertx, MONGO_SERVICE);

        vertx.eventBus().consumer(AIR_TEMPERATURE_ADDRESS, message -> {
            LOGGER.info("Message receive by the consumer : {}", message.body());
            final Temperature temperature = Json.decodeValue(message.body().toString(), Temperature.class);

            // Save temperature in mongodb
            repository.save(temperature, res -> {
                Temperature temperatureResult = res.result();
                LOGGER.info("Saved : {}", temperatureResult.toString());

                // Send air temperature in eventbus to be consumed by frontend
                vertx.eventBus().publish(AIR_TEMPERATURE_FRONTEND_ADDRESS, temperatureResult.toJson());
                LOGGER.info("New temperature send to eventbus to be consumed by frontend : {}",
                        temperatureResult.toString());
            });
        });
    }
}