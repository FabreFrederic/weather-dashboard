package io.fabre.frederic.weather.dashboard.backend;

import io.fabre.frederic.weather.dashboard.backend.data.EventBusAddresses;

public class AirTemperatureVerticle extends AbstractRawReadingManagerVerticle {

    public AirTemperatureVerticle() {
        super(EventBusAddresses.AIR_TEMPERATURE_RAW_ADDRESS.getValue(),
                EventBusAddresses.AIR_TEMPERATURE_NEW_ADDRESS.getValue());
    }
}