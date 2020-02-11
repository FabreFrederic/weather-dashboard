package io.fabre.frederic.weather.dashboard.scheduler.redis.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Service
public class RedisMessagePublisher implements MessagePublisher {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private ChannelTopic topic;
    @Autowired
    private JedisConnectionFactory jedisConnectionFactory;

    public RedisMessagePublisher() {
    }

    public RedisMessagePublisher(final RedisTemplate<String, Object> redisTemplate, final ChannelTopic topic) {
        this.redisTemplate = redisTemplate;
        this.topic = topic;
    }

    public void publish(final String message) {
        redisTemplate.convertAndSend(topic.getTopic(), message);
    }

    public void flushAll() {
        jedisConnectionFactory.getConnection().flushAll();
    }
}
