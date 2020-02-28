package io.fabre.frederic.weather.dashboard.backend;

import io.fabre.frederic.weather.dashboard.backend.data.EventBusAddress;

public class WaterTemperatureVerticle extends AbstractRawReadingManagerVerticle {

    public WaterTemperatureVerticle() {
        super(EventBusAddress.WATER_TEMPERATURE_RAW_ADDRESS.getValue(),
                EventBusAddress.WATER_TEMPERATURE_NEW_ADDRESS.getValue());
    }
}