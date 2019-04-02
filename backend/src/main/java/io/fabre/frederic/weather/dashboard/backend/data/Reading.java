package io.fabre.frederic.weather.dashboard.backend.data;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
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

    public Reading(JsonObject json) {
        ReadingConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        ReadingConverter.toJson(this, json);
        return json;
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
        return "Reading{" +
                "id='" + id + '\'' +
                ", date='" + date + '\'' +
                ", value='" + value + '\'' +
                ", sensorEnvironment=" + sensorEnvironment +
                ", sensorType=" + sensorType +
                '}';
    }
}
