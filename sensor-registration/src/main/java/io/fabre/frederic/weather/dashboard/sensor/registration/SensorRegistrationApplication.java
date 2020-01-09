package io.fabre.frederic.weather.dashboard.sensor.registration;

import io.fabre.frederic.weather.dashboard.sensor.registration.dao.SensorRepository;
import io.fabre.frederic.weather.dashboard.sensor.registration.model.Sensor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class SensorRegistrationApplication implements CommandLineRunner {

    private final SensorRepository sensorRepository;

    public SensorRegistrationApplication(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(SensorRegistrationApplication.class, args);
    }

    @Override
    public void run(String... args) {
        sensorRepository.save(new Sensor("Nimes", "name100"));
        sensorRepository.save(new Sensor("Montpellier", "name200"));
        sensorRepository.save(new Sensor("Los Angeles", "name300"));
    }
}