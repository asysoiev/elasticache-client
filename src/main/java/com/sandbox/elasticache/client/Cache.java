package com.sandbox.elasticache.client;

public interface Cache {
    String getValue(String key);

    void addValue(String key, String value);

    void updateValue(String key, String value);

    void deleteValue(String key);
}
