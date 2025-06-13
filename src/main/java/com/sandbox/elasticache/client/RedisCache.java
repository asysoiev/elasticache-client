package com.sandbox.elasticache.client;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;

public class RedisCache implements Cache {

    @Autowired
    @Resource(name = "redisTemplate")
    private ValueOperations<String, String> valueOperations;

    @Override
    public String getValue(String key) {
        return valueOperations.get(key);
    }

    @Override
    public void addValue(String key, String value) {
        valueOperations.set(key, value);
    }

    @Override
    public void updateValue(String key, String value) {
        valueOperations.set(key, value);
    }

    @Override
    public void deleteValue(String key) {
        valueOperations.getAndDelete(key);
    }
}
