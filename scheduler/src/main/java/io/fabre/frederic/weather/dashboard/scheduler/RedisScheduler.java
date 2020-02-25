package io.fabre.frederic.weather.dashboard.scheduler;

import io.fabre.frederic.weather.dashboard.scheduler.redis.queue.RedisMessagePublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class RedisScheduler {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisScheduler.class);
    private final RedisMessagePublisher redisMessagePublisher;

    public RedisScheduler(RedisMessagePublisher redisMessagePublisher) {
        this.redisMessagePublisher = redisMessagePublisher;
    }

    /**
     * Every day at midnight - 12 am (0 0 0 * * ?)
     * Every minute : "0 * * ? * *"
     */
    @Scheduled(cron="0 0 0 * * ?")
    public void flushAll() {
        LOGGER.info("Flush all Redis at {}", new Date());
        redisMessagePublisher.flushAll();
    }
}
