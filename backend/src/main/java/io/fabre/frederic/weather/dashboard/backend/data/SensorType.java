package io.fabre.frederic.weather.dashboard.backend.data;

public enum SensorType {
    TEMPERATURE("temperature"),
    PRESSURE("pressure");

    private final String value;

    SensorType(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
