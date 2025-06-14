package com.sandbox.elasticache.client;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

public class InMemoryCache implements Cache {

    private final Map<String, String> cache = new HashMap<>();

    @Override
    public String getValue(String key) {
        return cache.get(key);
    }

    @Override
    public Map<String, String> getAll() {
        return cache;
    }

    @Override
    public void addValue(String key, String value) {
        cache.put(key, value);
    }

    @Override
    public void updateValue(String key, String value) {
        cache.put(key, value);
    }

    @Override
    public void deleteValue(String key) {
        cache.remove(key);
    }

    @Override
    public void deleteAll() {
        cache.clear();
    }
}
