package io.fabre.frederic.weather.dashboard.backend;

public class WaterTemperatureVerticle extends AbstractTemperatureVerticle {

    private static final String WATER_TEMPERATURE_ADDRESS = "water.temperature.address";
    private static final String WATER_TEMPERATURE_FRONTEND_ADDRESS = "water.temperature.frontend.address";

    public WaterTemperatureVerticle() {
        super(WATER_TEMPERATURE_ADDRESS, WATER_TEMPERATURE_FRONTEND_ADDRESS);
    }
}