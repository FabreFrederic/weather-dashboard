package io.fabre.frederic.weather.dashboard.scheduler.redis.repo;

import io.fabre.frederic.weather.dashboard.scheduler.redis.model.Reading;
import org.springframework.data.repository.CrudRepository;

public interface ReadingRepository extends CrudRepository<Reading, String> {
}