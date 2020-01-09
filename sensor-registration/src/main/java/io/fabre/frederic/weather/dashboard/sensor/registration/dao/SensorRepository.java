package io.fabre.frederic.weather.dashboard.sensor.registration.dao;

import io.fabre.frederic.weather.dashboard.sensor.registration.model.Sensor;
import org.springframework.data.repository.CrudRepository;

public interface SensorRepository extends CrudRepository<Sensor, Integer> {

}
