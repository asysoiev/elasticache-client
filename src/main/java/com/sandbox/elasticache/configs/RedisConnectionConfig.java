package com.sandbox.elasticache.configs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.AnyNestedCondition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration.LettuceClientConfigurationBuilder;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import java.util.Set;

@Configuration
@Conditional(RedisConnectionConfig.RedisDefinedCondition.class)
public class RedisConnectionConfig {

    private static final Logger logger = LoggerFactory.getLogger(RedisConnectionConfig.class);

    private static final String STANDALONE_HOST = "redis.standalone.host";
    private static final String STANDALONE_PORT = "redis.standalone.port";
    private static final String CLUSTER_NODES = "redis.cluster.nodes";
    private static final String USE_SSL = "redis.ssl";

    @Bean
    @Conditional(RedisDefinedCondition.StandaloneCondition.class)
    RedisConfiguration standaloneConfiguration(
            @Value("${" + STANDALONE_HOST + ":}")
            String host,
            @Value("${" + STANDALONE_PORT + ":6379}")
            int port) {
        logger.info("Redis standalone cache is configured: {}:{}", host, port);
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(host);
        configuration.setPort(port);
        return configuration;
    }

    @Bean
    @Conditional(RedisDefinedCondition.ClusterCondition.class)
    RedisConfiguration clusterConfiguration(
            @Value("${" + CLUSTER_NODES + ":}")
            String nodes) {
        logger.info("Redis cluster cache is configured with nodes: {}", nodes);
        RedisClusterConfiguration configuration = new RedisClusterConfiguration(
                Set.of(nodes.split(",")));
        return configuration;
    }

    @Bean
    RedisConnectionFactory redisConnectionFactory(@Value("${" + USE_SSL + ":false}")
                                                  boolean useSSL,
                                                  RedisConfiguration redisConfiguration) {
        LettuceClientConfigurationBuilder clientConfigurationBuilder = LettuceClientConfiguration.builder();
        if (useSSL) {
            clientConfigurationBuilder.useSsl();
        }
        return new LettuceConnectionFactory(redisConfiguration, clientConfigurationBuilder.build());
    }

    @Bean
    RedisMessageListenerContainer redisContainer(RedisConnectionFactory redisConnectionFactory) {
        RedisMessageListenerContainer container
                = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        return container;
    }

    public static class RedisDefinedCondition extends AnyNestedCondition {

        public RedisDefinedCondition() {
            super(ConfigurationPhase.PARSE_CONFIGURATION);
        }

        @Conditional(StandaloneCondition.class)
        static class OnStandaloneCondition {
        }

        @Conditional(ClusterCondition.class)
        static class OnClusterCondition {
        }


        static class StandaloneCondition implements Condition {

            @Override
            public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
                return context.getEnvironment().containsProperty(STANDALONE_HOST)
                        && !context.getEnvironment().containsProperty(CLUSTER_NODES);
            }
        }

        static class ClusterCondition implements Condition {

            @Override
            public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
                return context.getEnvironment().containsProperty(CLUSTER_NODES);
            }
        }
    }
}
