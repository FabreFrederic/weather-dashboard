package io.fabre.frederic.weather.dashboard.backend;

import io.fabre.frederic.weather.dashboard.backend.data.Temperature;
import io.fabre.frederic.weather.dashboard.backend.data.TemperatureRepository;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WaterTemperatureVerticle extends AbstractVerticle {

    private static final Logger LOGGER = LoggerFactory.getLogger(WaterTemperatureVerticle.class);
    private static final String WATER_TEMPERATURE_ADDRESS = "water.temperature.address";
    private static final String MONGO_SERVICE = "mongo.service";
    private static final String WATER_TEMPERATURE_FRONTEND_ADDRESS = "water.temperature.frontend.address";

    @Override
    public void start() throws Exception {
        LOGGER.info("This verticle is starting");
        final TemperatureRepository repository = TemperatureRepository.createProxy(vertx, MONGO_SERVICE);

        vertx.eventBus().consumer(WATER_TEMPERATURE_ADDRESS, message -> {
            LOGGER.info("Message receive by the consumer : {}", message.body());
            final Temperature temperature = Json.decodeValue(message.body().toString(), Temperature.class);

            // Save temperature in mongodb
            repository.save(temperature, res -> {
                Temperature temperatureResult = res.result();
                LOGGER.info("Saved : {}", temperatureResult.toString());

                // Send water temperature in eventbus to be consumed by frontend
                vertx.eventBus().publish(WATER_TEMPERATURE_FRONTEND_ADDRESS, temperatureResult.toJson());
                LOGGER.info("New temperature send to eventbus to be consumed by frontend : {}",
                        temperatureResult.toString());
            });
        });
    }
}