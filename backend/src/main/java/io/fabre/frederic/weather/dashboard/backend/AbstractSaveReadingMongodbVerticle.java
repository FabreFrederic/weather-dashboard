package io.fabre.frederic.weather.dashboard.backend;

import io.fabre.frederic.weather.dashboard.backend.data.Reading;
import io.fabre.frederic.weather.dashboard.backend.data.TemperatureRepository;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractSaveReadingMongodbVerticle extends AbstractVerticle {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractSaveReadingMongodbVerticle.class);
    private static final String MONGO_SERVICE = "mongo.service";
    private String consumeNewReadingAddress;
    private String publishFrontendTemperatureAddress;

    /**
     *
     * @param consumeTemperatureAddress
     * @param publishFrontendTemperatureAddress
     */
    AbstractSaveReadingMongodbVerticle(final String consumeTemperatureAddress,
                                       final String publishFrontendTemperatureAddress) {
        this.consumeNewReadingAddress = consumeTemperatureAddress;
        this.publishFrontendTemperatureAddress = publishFrontendTemperatureAddress;
    }

    @Override
    public void start() {
        LOGGER.info("This verticle is starting");
        final TemperatureRepository repository = TemperatureRepository.createProxy(vertx, MONGO_SERVICE);

        vertx.eventBus().consumer(consumeNewReadingAddress, message -> {
            LOGGER.info("Message receive by the consumer : {}", message.body());
            final Reading reading = Json.decodeValue(message.body().toString(), Reading.class);

            // Save reading in mongodb
            repository.save(reading, res -> {
                Reading readingResult = res.result();
                LOGGER.info("Saved in mongodb : {}", readingResult);

                // Send reading in eventbus to be consumed by frontend
                vertx.eventBus().publish(publishFrontendTemperatureAddress, readingResult.toJson());
                LOGGER.info("New reading send to eventbus to be consumed by frontend : {}",
                        readingResult);
            });
        });
    }
}
