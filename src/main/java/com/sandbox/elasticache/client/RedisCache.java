package com.sandbox.elasticache.client;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ValueOperations;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class RedisCache implements Cache {

    @Autowired
    @Resource(name = "redisTemplate")
    private ValueOperations<String, String> valueOperations;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public String getValue(String key) {
        return valueOperations.get(key);
    }

    @Override
    public Map<String, String> getAll() {
        Map<String, String> result = new HashMap<>();
        for (String key : getAllKeys()) {
            result.put(key, valueOperations.get(key));
        }
        return result;
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

    @Override
    public void deleteAll() {
        redisTemplate.delete(getAllKeys());
    }

    private Set<String> getAllKeys() {
        ScanOptions scanOptions = ScanOptions.scanOptions()
                .match("*")
                .build();
        try (Cursor<String> cursor = redisTemplate.scan(scanOptions)) {
            return cursor.stream().collect(Collectors.toSet());
        }
    }
}
