package com.lqk.shardingjdbc.config.sharding.strategy;

import com.lqk.shardingjdbc.config.sharding.ShardingRoutesHolder;
import com.lqk.shardingjdbc.dao.entity.sharding.ShardingRoute;
import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author liqiankun
 * @date 2024/11/29 19:25
 * @description 分派存储相同CourseId数据的策略--可以使用复合分片算法
 **/
@Component
@ConditionalOnBean(ShardingRoutesHolder.class)
@ConditionalOnProperty(prefix = "spring.shardingsphere.rules.sharding", name = "auto-assign-route-strategy", havingValue = "sameCourseId")
public class SameCourseIdShardingRouteStrategy extends LeastShardingRouteStrategy {

    @Override
    public ShardingRoute autoAssignRoute(Collection<String> targetNames,
                                         PreciseShardingValue<String> preciseShardingValue) {
        // // 查询核算主体对应的纳税主体
        // Long companyId = accountCompanyManager.getCompanyId(preciseShardingValue.getValue());
        // if (companyId == null) {
        //     return super.autoAssignRoute(targetNames, preciseShardingValue);
        // }
        //
        // String logicTable = preciseShardingValue.getLogicTableName();
        // // 匹配已有路由中，是否存在相同公司的，存在取对应表
        // ShardingRoute sameCompanyRoute = routeMapper.getOneSameCompanyRoute(logicTable, companyId);
        // if (sameCompanyRoute != null) {
        //     ShardingRoute route = new ShardingRoute();
        //     route.setCompanyId(companyId);
        //     route.setLogicTable(logicTable);
        //     route.setColumnName(preciseShardingValue.getColumnName());
        //     route.setColumnValue(String.valueOf(preciseShardingValue.getValue()));
        //     route.setRealTable(sameCompanyRoute.getRealTable());
        //     return route;
        // }
        //
        // // 最后使用最少核算主体的表
        // ShardingRoute route = super.autoAssignRoute(targetNames, preciseShardingValue);
        // if (route == null) {
        //     return null;
        // }
        // route.setCompanyId(companyId);
        //
        // return route;
        // 本次走最少分配策略，如果需要实现可以使用复合分片策略，将courseId传过来
        return super.autoAssignRoute(targetNames, preciseShardingValue);
    }
}

