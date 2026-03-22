package com.example.L13_minor_project_01.config;

import com.example.L13_minor_project_01.dto.ProductResponseDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;


@Configuration
@EnableRedisHttpSession
public class RedisConfig {

    @Bean
    public RedisTemplate<String, ProductResponseDto> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, ProductResponseDto> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setValueSerializer(new JacksonJsonRedisSerializer<ProductResponseDto>(ProductResponseDto.class));
        return redisTemplate;
    }
}
