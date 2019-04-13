package io.fabre.frederic.weather.dashboard.backend;

import io.vertx.core.AbstractVerticle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractRawReadingManagerVerticle extends AbstractVerticle {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractRawReadingManagerVerticle.class);
    private String consumeRawReadingFromSensorAddress;
    private String publishNewReadingAddress;

    AbstractRawReadingManagerVerticle(final String consumeRawReadingFromSensorAddress,
                                      final String publishNewReadingAddress) {
        this.consumeRawReadingFromSensorAddress = consumeRawReadingFromSensorAddress;
        this.publishNewReadingAddress = publishNewReadingAddress;
    }

    @Override
    public void start() {
        LOGGER.info("This verticle is starting");

        vertx.eventBus().consumer(consumeRawReadingFromSensorAddress, message -> {
            LOGGER.info("Message receive by the consumer : {}", message.body());

            // TODO : check the new reading
            // before spreading a new reading, we have to set a checking system to be sure that the sensor is granted
            // to store a new reading

            vertx.eventBus().publish(publishNewReadingAddress, message.body());
        });
    }
}
