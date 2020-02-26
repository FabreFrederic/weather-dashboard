package io.fabre.frederic.weather.dashboard.scheduler.redis.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.stereotype.Service;

@Service
public class RedisMessagePublisher implements MessagePublisher {

    @Autowired
    private JedisConnectionFactory jedisConnectionFactory;

    public RedisMessagePublisher() {
    }

    public void flushAll() {
        jedisConnectionFactory.getConnection().flushAll();
    }
}