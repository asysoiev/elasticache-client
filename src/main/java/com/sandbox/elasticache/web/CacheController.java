package com.sandbox.elasticache.web;

import com.sandbox.elasticache.client.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/cache", produces = MediaType.APPLICATION_JSON_VALUE)
public class CacheController {

    @Autowired
    private Cache cache;

    @GetMapping(path = "/{key}")
    public String getValue(@PathVariable String key) {
        String value = cache.getValue(key);
        return ObjectUtils.isEmpty(value) ? "Not Found" : value;
    }

    @GetMapping
    public Map<String, String> getAllKeysAndValues() {
        return cache.getAll();
    }

    @PostMapping(path = "/{key}")
    public void addValue(@PathVariable String key, String value) {
        cache.addValue(key, value);
    }

    @PutMapping(path = "/{key}")
    public void updateValue(@PathVariable String key, String value) {
        cache.updateValue(key, value);
    }

    @DeleteMapping(path = "/{key}")
    public void deleteValue(@PathVariable String key) {
        cache.deleteValue(key);
    }

    @DeleteMapping
    public void deleteAll() {
        cache.deleteAll();
    }
}
