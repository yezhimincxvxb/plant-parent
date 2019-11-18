package com.moguying.plant.core.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    @Value("${cache.default-ttl}")
    private long ttl;

    @Override
    @Bean
    public KeyGenerator keyGenerator() {
        return (o, method, objects) -> {
            StringBuffer sb = new StringBuffer();
            sb.append(o.getClass().getName());
            sb.append(method.getName());
            for (Object object : objects) {
                sb.append(object.toString());
            }
            return sb.toString();
        };
    }

    @Bean
    public RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        FastJsonRedisSerializer<JSON> jsonFastJsonRedisSerializer = new FastJsonRedisSerializer<>(JSON.class);
        template.setConnectionFactory(redisConnectionFactory);
        template.setValueSerializer(jsonFastJsonRedisSerializer);
        template.setHashValueSerializer(jsonFastJsonRedisSerializer);
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setDefaultSerializer(jsonFastJsonRedisSerializer);
        return template;
    }


    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory);
        FastJsonRedisSerializer<Object> jsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);
        RedisSerializationContext.SerializationPair<Object> pair = RedisSerializationContext.SerializationPair.fromSerializer(jsonRedisSerializer);
        RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(pair);
        cacheConfiguration = cacheConfiguration.entryTtl(Duration.ofSeconds(ttl));
        RedisCacheManager cacheManager = new RedisCacheManager(redisCacheWriter, cacheConfiguration);
        ParserConfig.getGlobalInstance().addAccept("com.moguying.plant.entity.");
        return cacheManager;
    }


}

