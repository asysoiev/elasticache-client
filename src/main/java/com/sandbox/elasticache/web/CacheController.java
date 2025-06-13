package com.sandbox.elasticache.web;

import com.sandbox.elasticache.client.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/cache", produces = MediaType.APPLICATION_JSON_VALUE)
public class CacheController {

    @Autowired
    private Cache cache;

    @GetMapping
    public String getValue(String key) {
        String value = cache.getValue(key);
        return ObjectUtils.isEmpty(value) ? "Not Found" : value;
    }

    @PostMapping
    public void addValue(String key, String value) {
        cache.addValue(key, value);
    }

    @PutMapping
    public void updateValue(String key, String value) {
        cache.updateValue(key, value);
    }

    @DeleteMapping
    public void deleteValue(String key) {
        cache.deleteValue(key);
    }
}
