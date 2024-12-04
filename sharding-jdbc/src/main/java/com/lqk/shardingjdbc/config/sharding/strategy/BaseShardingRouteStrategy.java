package com.lqk.shardingjdbc.config.sharding.strategy;

import com.lqk.shardingjdbc.config.redis.RedisLockUtils;
import com.lqk.shardingjdbc.config.sharding.ShardingRoutesHolder;
import com.lqk.shardingjdbc.constant.RedisLockConstants;
import com.lqk.shardingjdbc.dao.entity.sharding.ShardingRoute;
import java.util.Collection;

import com.lqk.shardingjdbc.dao.mapper.ShardingRouteMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author liqiankun
 * @date 2024/11/29 19:24
 * @description
 **/
@Slf4j
public abstract class BaseShardingRouteStrategy {

    @Autowired
    protected ShardingRoutesHolder routesHolder;
    @Autowired
    protected ShardingRouteMapper routeMapper;
    @Autowired
    protected RedisLockUtils redisLockUtils;

    /**
     * 匹配真实表
     *
     * @param targetNames 目标表
     * @param preciseShardingValue 精确分片值
     * @return 真实表
     */
    public String matchRealTable(Collection<String> targetNames,
                                 PreciseShardingValue<String> preciseShardingValue) {
        // 逻辑表
        String logicTable = preciseShardingValue.getLogicTableName();
        // 分片键值
        String value = preciseShardingValue.getValue();

        String realTable = routesHolder.getRealTableFromCache(logicTable, value);
        if (StringUtils.isNotBlank(realTable)) {
            return realTable;
        }

        String redisLockKey = getRedisLockKey(preciseShardingValue);
        try {
            // 分布式锁，
            boolean lock = false;
            while (!lock) {
                lock = redisLockUtils.tryLock(redisLockKey, 0, -1);
                if (!lock) {
                    Thread.sleep(50);
                    continue;
                }

                // 抢到锁，再次检查缓存、数据表中是否已存在路由
                realTable = routesHolder.getRealTableFromCache(logicTable, value);
                if (StringUtils.isNotBlank(realTable)) {
                    return realTable;
                }

                // 再次检查数据表中是否已存在路由
                realTable = routesHolder.getRealTableFromDb(preciseShardingValue);
                if (StringUtils.isNotBlank(realTable)) {
                    return realTable;
                }

                ShardingRoute route;
                // 目标表仅有一个直接返回，没有分表的情况
                if(CollectionUtils.isNotEmpty(targetNames) && targetNames.size() == 1){
                    route = new ShardingRoute();
                    route.setColumnName(preciseShardingValue.getColumnName());
                    route.setColumnValue(String.valueOf(preciseShardingValue.getValue()));
                    route.setRealTable(targetNames.stream().findAny().orElse(logicTable));
                    route.setLogicTable(logicTable);
                } else {
                    // 自动分配一个表
                    route = autoAssignRoute(targetNames, preciseShardingValue);
                }

                if (route != null) {
                    routesHolder.saveRoute(route);
                    return route.getRealTable();
                }
            }
        } catch (InterruptedException interruptedException) {
            log.error("自动分配分表路由线程意外中断异常:{}", preciseShardingValue);
            log.error("自动分配分表路由线程意外中断异常:", interruptedException);
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            log.error("自动分配分表路由异常:{}", preciseShardingValue);
            log.error("自动分配分表路由异常:", e);
        } finally {
            redisLockUtils.unlockIfHeldByCurrentThread(redisLockKey);
        }

        return null;
    }

    /**
     * 获取Redis Lock Key
     *
     * @param preciseShardingValue 精确分片值
     * @return Redis Lock Key
     */
    protected String getRedisLockKey(PreciseShardingValue<String> preciseShardingValue) {
        String logicTable = preciseShardingValue.getLogicTableName();
        String column = preciseShardingValue.getColumnName();
        String value = preciseShardingValue.getValue();
        // tax:lock:sharding_jdbc:user:userid#1
        return RedisLockConstants.SHARDING_JDBC_LOCK + logicTable + ":" + column + "#" + value;
    }

    /**
     * 自动分派路由
     *
     * @param targetNames 目标表
     * @param preciseShardingValue 精确分片值
     * @return 路由
     */
    public abstract ShardingRoute autoAssignRoute(Collection<String> targetNames,
                                                  PreciseShardingValue<String> preciseShardingValue);
}

