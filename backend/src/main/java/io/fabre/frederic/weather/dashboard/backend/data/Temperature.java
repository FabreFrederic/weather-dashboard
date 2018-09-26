package io.fabre.frederic.weather.dashboard.backend.data;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
public class Temperature {
    private String id;
    private String date;
    private String value;
    private SensorEnvironment sensorEnvironment;
    public static final String DB_TABLE = Temperature.class.getName();

    public Temperature() {
    }

    public Temperature(String id, String date, String value) {
        this.id = id;
        this.date = date;
        this.value = value;
    }

    public Temperature(JsonObject json) {
        TemperatureConverter.fromJson(json, this);
    }

    public JsonObject toJson(){
        JsonObject json = new JsonObject();
        TemperatureConverter.toJson(this, json);
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

    public static String getDbTable() {
        return DB_TABLE;
    }

    @Override
    public String toString() {
        return "Temperature{" +
                "id='" + id + '\'' +
                ", date='" + date + '\'' +
                ", value=" + value +
                ", sensorEnvironment=" + sensorEnvironment +
                '}';
    }
}
