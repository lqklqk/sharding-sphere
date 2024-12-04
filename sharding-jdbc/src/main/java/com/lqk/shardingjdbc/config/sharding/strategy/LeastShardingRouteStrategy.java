package com.lqk.shardingjdbc.config.sharding.strategy;

import com.lqk.shardingjdbc.config.sharding.ShardingRoutesHolder;
import com.lqk.shardingjdbc.dao.entity.sharding.ShardingRealTableCount;
import com.lqk.shardingjdbc.dao.entity.sharding.ShardingRoute;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liqiankun
 * @date 2024/11/29 19:24
 * @description 分派分片键值最少的真实表的策略
 **/
@Component
@Slf4j
@ConditionalOnBean(ShardingRoutesHolder.class)
@ConditionalOnProperty(prefix = "spring.shardingsphere.rules.sharding", name = {"auto-assign-route-strategy"}, havingValue = "least", matchIfMissing = true)
public class LeastShardingRouteStrategy extends BaseShardingRouteStrategy {

    @Override
    public ShardingRoute autoAssignRoute(Collection<String> targetNames,
                                         PreciseShardingValue<String> preciseShardingValue) {
        String logicTable = preciseShardingValue.getLogicTableName();

        List<ShardingRealTableCount> realTableCounts = routeMapper.countRealTable(logicTable);
        if (CollectionUtils.isEmpty(realTableCounts)) {
            String realName = targetNames.stream().findFirst().orElse(null);
            ShardingRealTableCount tableCount = new ShardingRealTableCount();
            tableCount.setRealTable(realName);
            tableCount.setCnt(0);
            realTableCounts.add(tableCount);
        }

        List<String> allRealTable = realTableCounts.stream()
                .map(ShardingRealTableCount::getRealTable).collect(Collectors.toList());

        // 取路由中没有的真实表
        for (String targetName : targetNames) {
            if (allRealTable.contains(targetName)) {
                continue;
            }

            ShardingRoute route = new ShardingRoute();
            route.setColumnName(preciseShardingValue.getColumnName());
            route.setColumnValue(String.valueOf(preciseShardingValue.getValue()));
            route.setRealTable(targetName);
            route.setLogicTable(logicTable);
            return route;
        }

        // 取路由中存储最少分片键值的表
        ShardingRealTableCount leased = realTableCounts.get(0);
        ShardingRoute route = new ShardingRoute();
        route.setColumnName(preciseShardingValue.getColumnName());
        route.setColumnValue(String.valueOf(preciseShardingValue.getValue()));
        route.setRealTable(leased.getRealTable());
        route.setLogicTable(logicTable);
        return route;
    }
}

