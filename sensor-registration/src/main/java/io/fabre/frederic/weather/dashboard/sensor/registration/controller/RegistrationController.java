package io.fabre.frederic.weather.dashboard.sensor.registration.controller;

import io.fabre.frederic.weather.dashboard.sensor.registration.dao.SensorRepository;
import io.fabre.frederic.weather.dashboard.sensor.registration.model.Sensor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Api("Sensors API")
@RestController
public class RegistrationController {

    private final SensorRepository sensorRepository;

    @Autowired
    public RegistrationController(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    @ApiOperation(value = "Return all the sensors")
    @GetMapping(value = "/sensors")
    public Iterable<Sensor> getSensors() {
        return sensorRepository.findAll();
    }

    @ApiOperation(value = "Return the sensor")
    @GetMapping(value = "/sensor/{id}")
    public ResponseEntity<Sensor> getSensor(final @PathVariable int id) {
        Optional<Sensor> sensor = sensorRepository.findById(id);
        return sensor.map(value ->
                ResponseEntity.status(HttpStatus.OK).body(value)).
                orElseGet(() -> new ResponseEntity(HttpStatus.NOT_FOUND));
    }

    @ApiOperation(value = "Register a new sensor")
    @PostMapping(value = "sensor")
    public ResponseEntity<Void> addSensor(final @RequestBody Sensor newSensor) {
        final Sensor sensor = sensorRepository.save(newSensor);

        if (sensor == null) {
            return ResponseEntity.noContent().build();
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(sensor.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @ApiOperation(value = "Delete a sensor")
    @DeleteMapping(value = "sensor")
    public ResponseEntity<Void> deleteSensor(final @RequestBody Sensor oldSensor) {
        sensorRepository.delete(oldSensor);
        return ResponseEntity.accepted().build();
    }

    @ApiOperation(value = "Update a sensor")
    @PutMapping(value = "sensor/{id}")
    public ResponseEntity<Void> renameSensor(final @PathVariable int id,
                                             final @RequestBody String newName) {
        final Optional<Sensor> sensor = sensorRepository.findById(id);
        if (sensor.isPresent()) {
            sensor.get().setName(newName);
            sensorRepository.save(sensor.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
