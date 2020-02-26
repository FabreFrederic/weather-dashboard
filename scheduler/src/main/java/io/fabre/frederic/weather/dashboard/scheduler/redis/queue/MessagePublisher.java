package io.fabre.frederic.weather.dashboard.scheduler.redis.queue;

public interface MessagePublisher {

    /**
     * Flush all the keys
     */
    void flushAll();
}
