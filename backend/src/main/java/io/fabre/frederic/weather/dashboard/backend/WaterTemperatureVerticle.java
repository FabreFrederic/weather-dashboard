package io.fabre.frederic.weather.dashboard.backend;

import io.fabre.frederic.weather.dashboard.backend.data.EventBusAddresses;

public class WaterTemperatureVerticle extends AbstractRawReadingManagerVerticle {

    public WaterTemperatureVerticle() {
        super(EventBusAddresses.WATER_TEMPERATURE_RAW_ADDRESS.getValue(),
                EventBusAddresses.WATER_TEMPERATURE_NEW_ADDRESS.getValue());
    }
}