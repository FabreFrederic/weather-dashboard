package io.fabre.frederic.weather.dashboard.scheduler.redis.queue;

public interface MessagePublisher {

    void publish(final String message);
}
