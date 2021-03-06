package io.fabre.frederic.weather.dashboard.backend.data;

public enum SensorEnvironment {
    WATER("water"),
    AIR("air");

    private final String value;

    SensorEnvironment(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
