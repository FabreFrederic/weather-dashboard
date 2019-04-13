package io.fabre.frederic.weather.dashboard.backend;

import io.fabre.frederic.weather.dashboard.backend.data.EventBusAddresses;
import io.fabre.frederic.weather.dashboard.backend.data.Reading;
import io.fabre.frederic.weather.dashboard.backend.data.TemperatureRepository;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.Json;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SaveReadingMongodbVerticle extends AbstractVerticle {

    private static final Logger LOGGER = LoggerFactory.getLogger(SaveReadingMongodbVerticle.class);
    private static final String MONGO_SERVICE = "mongo.service";

    @Override
    public void start() {
        LOGGER.info("This verticle is starting");
        saveReading();
    }

    private void saveReading() {
        final TemperatureRepository repository = TemperatureRepository.createProxy(vertx, MONGO_SERVICE);

        getNewReadingEventBusAddressesOnly().forEach(address -> {
            LOGGER.info("Consume this address : {}", address.getValue());
            vertx.eventBus().consumer(address.getValue(), message -> {
                final Reading reading = Json.decodeValue(message.body().toString(), Reading.class);

                // Save reading in mongodb
                repository.save(reading, res -> {
                    Reading readingResult = res.result();
                    LOGGER.info("Saved in mongodb : {}", readingResult);
                });
            });
        });
    }

    private List<EventBusAddresses> getNewReadingEventBusAddressesOnly() {
        List<EventBusAddresses> addresses = Arrays.asList(EventBusAddresses.values());

        return addresses.stream().
                filter(address -> StringUtils.containsIgnoreCase(address.getValue(), "new")).
                collect(Collectors.toList());
    }
}
