package com.lqk.shardingjdbc.config.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author liqiankun
 * @date 2024/11/29 15:16
 * @description
 **/
@Configuration
public class RedisTemplateConfig {
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory){
        RedisTemplate<String,Object> template=new RedisTemplate<>();
        template.setConnectionFactory(factory);
        //key序列化方式
        template.setKeySerializer(new StringRedisSerializer());
        //value序列化
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        //key hashmap序列化
        template.setHashKeySerializer(new GenericJackson2JsonRedisSerializer());
        //value hashmap序列化
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }
}
