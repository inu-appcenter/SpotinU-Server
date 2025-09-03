package kr.inuappcenter.spotinu.global.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableCaching
public class CacheConfig {

  @Bean
  public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
    RedisCacheConfiguration defaultConfig  = RedisCacheConfiguration.defaultCacheConfig()
      .entryTtl(Duration.ofHours(24)) // TTL 24시간
      .disableCachingNullValues()
      .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(
        new StringRedisSerializer()))
      .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(
        new GenericJackson2JsonRedisSerializer()));

    // 캐시 이름별 TTL 설정
    Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();

    RedisCacheConfiguration spotsConfig = RedisCacheConfiguration.defaultCacheConfig()
      .entryTtl(Duration.ofHours(2))  // TTL 2시간
      .disableCachingNullValues()
      .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(
        new StringRedisSerializer()))
      .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(
        new GenericJackson2JsonRedisSerializer()));
    cacheConfigurations.put("spots", spotsConfig);

    RedisCacheConfiguration searchConfig = RedisCacheConfiguration.defaultCacheConfig()
      .entryTtl(Duration.ofMinutes(10)) // TTL 10분
      .disableCachingNullValues()
      .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(
        new StringRedisSerializer()))
      .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(
        new GenericJackson2JsonRedisSerializer()));
    cacheConfigurations.put("spots_search", searchConfig);

    return RedisCacheManager.builder(redisConnectionFactory)
      .cacheDefaults(defaultConfig)
      .withInitialCacheConfigurations(cacheConfigurations)
      .build();
  }
}
