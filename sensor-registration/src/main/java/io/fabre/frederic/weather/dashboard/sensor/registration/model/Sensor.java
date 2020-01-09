package io.fabre.frederic.weather.dashboard.sensor.registration.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sensor")
public class Sensor {
    @Id
    @GeneratedValue
    private int id;
    private String location;
    private String name;

    public Sensor() {
    }

    public Sensor(final int id, final String location, final String name) {
        this.id = id;
        this.location = location;
        this.name = name;
    }

    public Sensor(final String location, final String name) {
        this.location = location;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public Sensor setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Sensor{");
        sb.append("id=").append(id);
        sb.append(", location='").append(location).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
