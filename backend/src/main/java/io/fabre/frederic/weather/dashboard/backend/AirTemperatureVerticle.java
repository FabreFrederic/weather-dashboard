package io.fabre.frederic.weather.dashboard.backend;

public class AirTemperatureVerticle extends AbstractTemperatureVerticle {

    private static final String AIR_TEMPERATURE_ADDRESS = "air.temperature.address";
    private static final String AIR_TEMPERATURE_FRONTEND_ADDRESS = "air.temperature.frontend.address";

    public AirTemperatureVerticle() {
        super(AIR_TEMPERATURE_ADDRESS, AIR_TEMPERATURE_FRONTEND_ADDRESS);
    }
}