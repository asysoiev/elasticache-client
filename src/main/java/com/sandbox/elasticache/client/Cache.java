package com.sandbox.elasticache.client;

import java.util.Map;

public interface Cache {
    String getValue(String key);

    Map<String, String> getAll();

    void addValue(String key, String value);

    void updateValue(String key, String value);

    void deleteValue(String key);

    void deleteAll();
}
