package com.sandbox.elasticache.configs;

import com.sandbox.elasticache.client.Cache;
import com.sandbox.elasticache.client.InMemoryCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.AllNestedConditions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
@Conditional(InMemoryCacheConfig.InMemoryCacheCondition.class)
public class InMemoryCacheConfig {

    private static final Logger logger = LoggerFactory.getLogger(InMemoryCacheConfig.class);

    public InMemoryCacheConfig() {
        logger.warn("Not persisted cache is configured");
    }

    @Bean
    public Cache inMemoryCache() {
        return new InMemoryCache();
    }

    public static class InMemoryCacheCondition extends AllNestedConditions {

        public InMemoryCacheCondition() {
            super(ConfigurationPhase.REGISTER_BEAN);
        }

        @ConditionalOnMissingBean(RedisCacheConfig.class)
        static class OnAbsentRedisConnectionCondition {
        }

    }
}
