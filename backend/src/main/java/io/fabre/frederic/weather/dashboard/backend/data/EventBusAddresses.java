package io.fabre.frederic.weather.dashboard.backend.data;

public enum EventBusAddresses {

    AIR_TEMPERATURE_RAW_ADDRESS("air.temperature.raw.address"),
    AIR_TEMPERATURE_NEW_ADDRESS("air.temperature.new.address"),
    WATER_TEMPERATURE_RAW_ADDRESS("water.temperature.raw.address"),
    WATER_TEMPERATURE_NEW_ADDRESS("water.temperature.new.address"),
    AIR_TEMPERATURE_MIN_ADDRESS("air.temperature.min.address"),
    AIR_TEMPERATURE_MAX_ADDRESS("air.temperature.max.address"),
    WATER_TEMPERATURE_MIN_ADDRESS("water.temperature.min.address"),
    WATER_TEMPERATURE_MAX_ADDRESS("water.temperature.max.address");

    private String value;

    EventBusAddresses(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}