package io.fabre.frederic.weather.dashboard.backend.data;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
public class Sensor {
    private String id;
    private String name;

    public static final String DB_TABLE = Sensor.class.getName();

    public Sensor() {
    }

    public Sensor(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Sensor(JsonObject json) {
        this();
        SensorConverter.fromJson(json, this);
    }

    public JsonObject toJson(){
        JsonObject json = new JsonObject();
        SensorConverter.toJson(this, json);
        return json;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return Json.encodePrettily(this);
    }

}
