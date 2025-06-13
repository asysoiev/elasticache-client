package com.sandbox.elasticache.configs;

import com.sandbox.elasticache.client.Cache;
import com.sandbox.elasticache.client.RedisCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@Import({RedisConnectionConfig.class})
@Conditional(RedisConnectionConfig.RedisDefinedCondition.class)
public class RedisCacheConfig {

    public static final Logger logger = LoggerFactory.getLogger(RedisCacheConfig.class);

    public RedisCacheConfig() {
        logger.info("Redis cache is configured");
    }

    @Bean
    public Cache redisCache() {
        return new RedisCache();
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }
}
