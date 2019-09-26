package io.fabre.frederic.weather.dashboard.backend;

import io.fabre.frederic.weather.dashboard.backend.data.EventBusAddress;

public class AirTemperatureVerticle extends AbstractRawReadingManagerVerticle {

    public AirTemperatureVerticle() {
        super(EventBusAddress.AIR_TEMPERATURE_RAW_ADDRESS.getValue(),
                EventBusAddress.AIR_TEMPERATURE_NEW_ADDRESS.getValue());
    }
}