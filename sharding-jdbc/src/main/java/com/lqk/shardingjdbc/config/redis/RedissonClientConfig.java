package com.lqk.shardingjdbc.config.redis;

import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author liqiankun
 * @date 2024/12/2 17:01
 * @description
 **/
@Configuration
public class RedissonClientConfig {
    @Autowired
    private RedisConfigProperties redisConfigProperties;


    private Config getConfig() {
        Config config = new Config();

        boolean clusterMode = false;
        if (Objects.isNull(redisConfigProperties.getHost())) {
            clusterMode = true;
        }

        if (clusterMode) {
            List<String> nodeList = Arrays.asList(
                    redisConfigProperties.getCluster().getNodes().split(","));
            List<String> clusterNodes = new ArrayList<>();
            for (int i = 0; i < nodeList.size(); i++) {
                clusterNodes.add("redis://" + nodeList.get(i));
            }
            ClusterServersConfig clusterServersConfig = config.useClusterServers()
                    .addNodeAddress(clusterNodes.toArray(new String[clusterNodes.size()]));
            //设置密码
            clusterServersConfig.setPassword(redisConfigProperties.getPassword());
        } else {
            SingleServerConfig singleServerConfig = config.useSingleServer();
            if (StringUtils.isNotBlank(redisConfigProperties.getPassword())) {
                singleServerConfig.setPassword(redisConfigProperties.getPassword());
            }
            singleServerConfig.setAddress(
                    "redis://" + redisConfigProperties.getHost() + ":" + redisConfigProperties.getPort());
            singleServerConfig.setDatabase(redisConfigProperties.getDatabase());
        }
        return config;
    }


    @Bean
    public RedissonClient getRedisson() {
        return Redisson.create(getConfig());
    }
}
