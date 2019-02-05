package io.fabre.frederic.weather.dashboard.backend.data;

public enum EventBusAddresses {
    ADDRESSES(".address");

    private final String value;

    EventBusAddresses(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}