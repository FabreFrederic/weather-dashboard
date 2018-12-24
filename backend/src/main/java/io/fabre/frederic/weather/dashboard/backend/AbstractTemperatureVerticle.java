package io.fabre.frederic.weather.dashboard.backend;

import io.fabre.frederic.weather.dashboard.backend.data.Temperature;
import io.fabre.frederic.weather.dashboard.backend.data.TemperatureRepository;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractTemperatureVerticle extends AbstractVerticle {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractTemperatureVerticle.class);
    private static final String MONGO_SERVICE = "mongo.service";
    private String consumeTemperatureAddress;
    private String publishFrontendTemperatureAddress;

    AbstractTemperatureVerticle(String consumeTemperatureAddress, String publishFrontendTemperatureAddress) {
        this.consumeTemperatureAddress = consumeTemperatureAddress;
        this.publishFrontendTemperatureAddress = publishFrontendTemperatureAddress;
    }

    @Override
    public void start() {
        LOGGER.info("This verticle is starting");
        final TemperatureRepository repository = TemperatureRepository.createProxy(vertx, MONGO_SERVICE);

        vertx.eventBus().consumer(consumeTemperatureAddress, message -> {
            LOGGER.info("Message receive by the consumer : {}", message.body());
            final Temperature temperature = Json.decodeValue(message.body().toString(), Temperature.class);

            // Save temperature in mongodb
            repository.save(temperature, res -> {
                Temperature temperatureResult = res.result();
                LOGGER.info("Saved : {}", temperatureResult);

                // Send temperature in eventbus to be consumed by frontend
                vertx.eventBus().publish(publishFrontendTemperatureAddress, temperatureResult.toJson());
                LOGGER.info("New temperature send to eventbus to be consumed by frontend : {}",
                        temperatureResult);
            });
        });
    }
}
