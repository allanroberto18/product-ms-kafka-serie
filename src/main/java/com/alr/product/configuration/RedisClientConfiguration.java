package com.alr.product.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisClientConfiguration {
  private final Logger logger = LoggerFactory.getLogger(RedisClientConfiguration.class);

  @Value("${spring.redis.host}")
  private String redisHostName;

  @Value("${spring.redis.port}")
  private int redisPort;

  @Value("${spring.redis.database}")
  private int redisDatabase;

  @Bean
  public JedisConnectionFactory jedisConnectionFactory() {
    logger.info(String.format(
        "| Redis Connection Information: -> host: %s | port: %s | database: %s |",
          redisHostName,
          redisPort,
          redisDatabase
        )
    );

    RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(redisHostName, redisPort);
    redisStandaloneConfiguration.setDatabase(redisDatabase);
    return new JedisConnectionFactory(redisStandaloneConfiguration);
  }

  @Bean
  public RedisTemplate<?, ?> redisTemplate() {
    RedisTemplate<?, ?> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(jedisConnectionFactory());
    return redisTemplate;
  }
}
