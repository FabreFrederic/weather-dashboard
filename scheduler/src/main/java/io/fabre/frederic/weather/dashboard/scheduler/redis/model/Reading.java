package io.fabre.frederic.weather.dashboard.scheduler.redis.model;

import org.springframework.data.redis.core.RedisHash;

@RedisHash("Reading")
public class Reading {
    private String id;
    private String date;
    private String value;
    private SensorEnvironment sensorEnvironment;
    private SensorType sensorType;

    public static final String COLLECTION = "weather.dashboard.collection";

    public Reading() {
    }

    public Reading(String id, String date, String value, SensorEnvironment sensorEnvironment, SensorType sensorType) {
        this.id = id;
        this.date = date;
        this.value = value;
        this.sensorEnvironment = sensorEnvironment;
        this.sensorType = sensorType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public SensorEnvironment getSensorEnvironment() {
        return sensorEnvironment;
    }

    public void setSensorEnvironment(SensorEnvironment sensorEnvironment) {
        this.sensorEnvironment = sensorEnvironment;
    }

    public SensorType getSensorType() {
        return sensorType;
    }

    public void setSensorType(SensorType sensorType) {
        this.sensorType = sensorType;
    }

    public static String getCollection() {
        return COLLECTION;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Reading{");
        sb.append("id='").append(id).append('\'');
        sb.append(", date='").append(date).append('\'');
        sb.append(", value='").append(value).append('\'');
        sb.append(", sensorEnvironment=").append(sensorEnvironment);
        sb.append(", sensorType=").append(sensorType);
        sb.append('}');
        return sb.toString();
    }
}
