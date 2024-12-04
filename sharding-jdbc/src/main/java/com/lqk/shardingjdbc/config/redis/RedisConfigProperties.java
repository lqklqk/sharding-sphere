package com.lqk.shardingjdbc.config.redis;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author liqiankun
 * @date 2024/12/2 17:08
 * @description
 **/
@ConfigurationProperties(prefix = "spring.data.redis")
@Component
@Data
public class RedisConfigProperties {
    private String host;
    private Integer port;
    private String password;
    private int database;
    private Cluster cluster;

    @Getter
    @Setter
    public static class Cluster {

        private String nodes;
        private int maxRedirects;
    }
}
