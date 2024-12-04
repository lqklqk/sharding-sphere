package com.lqk.shardingjdbc.config.sharding;

import com.lqk.shardingjdbc.constant.RedisKeyConstants;
import com.lqk.shardingjdbc.constant.RedisLockConstants;
import com.lqk.shardingjdbc.dao.entity.sharding.ShardingRoute;
import com.lqk.shardingjdbc.dao.mapper.ShardingRouteMapper;
import jodd.util.StringUtil;
import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author liqiankun
 * @date 2024/11/29 19:26
 * @description
 **/
@Component
@ConditionalOnProperty(prefix = "spring.shardingsphere", name = "enabled", havingValue = "true", matchIfMissing = true)
public class ShardingRoutesHolder {

    @Autowired
    private ShardingRouteMapper routeMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 根据key获取真实表
     *
     * @param logicTable 逻辑表
     * @param value 键值
     * @return 真实表
     */
    public String getRealTableFromCache(String logicTable, String value) {
        return redisTemplate.opsForValue().get(RedisKeyConstants.SHARDING_ROUTE + logicTable + ":" + value);
    }

    /**
     * 保存匹配的路由关系
     *
     * @param route 路由
     */
    public void saveRoute(ShardingRoute route) {
        if (StringUtil.isEmpty(route.getRealTable())) {
            return;
        }

        routeMapper.insert(route);

        // 缓存路由
        redisTemplate.opsForValue().set(RedisKeyConstants.SHARDING_ROUTE + route.getLogicTable() + ":"
                + route.getColumnValue(), route.getRealTable());
    }

    /**
     * 检查数据表中是否已存在路由
     *
     * @param shardingValue 精确分片值
     * @return 路由
     */
    public String getRealTableFromDb(PreciseShardingValue<String> shardingValue) {
        ShardingRoute route = routeMapper.get(shardingValue.getLogicTableName(),
                shardingValue.getColumnName(), String.valueOf(shardingValue.getValue()));
        if (route == null) {
            return null;
        }

        // 缓存路由
        redisTemplate.opsForValue().set(RedisKeyConstants.SHARDING_ROUTE + route.getLogicTable() + ":"
                + route.getColumnValue(), route.getRealTable());

        return route.getRealTable();
    }
}
