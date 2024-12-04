package com.lqk.shardingjdbc.config.sharding;

import com.lqk.shardingjdbc.component.AppContextHolder;
import lombok.Getter;
import lombok.Setter;
import org.apache.shardingsphere.sharding.spi.KeyGenerateAlgorithm;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Objects;
import java.util.Properties;

/**
 * @author liqiankun
 * @date 2024/11/29 14:35
 * @description 自定义主键生成策略
 **/
public class RedisShardingKeyGenerator implements KeyGenerateAlgorithm {

    private StringRedisTemplate redisTemplate;

    private static final String KEY = "strategy-key";
    private static final String KEY_PREFIX = "lqk:sharding:key-generator:";

    @Getter
    @Setter
    private Properties properties = new Properties();

    @Override
    public Comparable<?> generateKey() {
        if (Objects.isNull(redisTemplate)) {
            redisTemplate = AppContextHolder.getBeanByClass(StringRedisTemplate.class);
        }
        // 主键自增策略
        return String.valueOf(redisTemplate.opsForValue().increment(getKey(), 1));
    }

    private String getKey() {
        return KEY_PREFIX + properties.getProperty(KEY);
    }

    @Override
    public Properties getProps() {
        return this.properties;
    }

    @Override
    public void init(Properties props) {
        this.properties = props;
    }

    @Override
    public String getType() {
        return "redisSharding";
    }
}
