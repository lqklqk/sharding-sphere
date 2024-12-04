package com.lqk.shardingjdbc.config.sharding.algorithem;

import com.lqk.shardingjdbc.component.AppContextHolder;
import com.lqk.shardingjdbc.config.sharding.strategy.BaseShardingRouteStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;

import java.util.Collection;
import java.util.Properties;

/**
 * @author liqiankun
 * @date 2024/11/29 17:51
 * @description
 **/
@Slf4j
public class UserStandardShardingAlgorithm implements StandardShardingAlgorithm<String> {

    private BaseShardingRouteStrategy routeStrategy;
    private Properties props;

    /**
     * 精确分片算法
     * @param availableTargetNames available data sources or table names
     * @param preciseShardingValue sharding value
     * @return
     */
    @Override
    public String doSharding(Collection<String> availableTargetNames,
                             PreciseShardingValue<String> preciseShardingValue) {
        if (routeStrategy == null) {
            routeStrategy = AppContextHolder.getBeanByClass(BaseShardingRouteStrategy.class);
        }

        String realTable = routeStrategy.matchRealTable(availableTargetNames, preciseShardingValue);

        if (availableTargetNames.contains(realTable)) {
            return realTable;
        }

        log.error("分表错误：未匹配到可用真实表，目标表[{}], 精确分片值[{}]",
                availableTargetNames, preciseShardingValue);
        throw new RuntimeException("未匹配到可用真实表，请检查配置！");
    }

    /**
     * 范围分片算法：适用于 >、>=、<、<=操作，目前是禁止写这样的sql
     * @param availableTargetNames available data sources or table names
     * @param shardingValue sharding value
     * @return
     */
    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<String> shardingValue) {
        log.error("分表错误：不允许范围查询，目标表[{}], 范围分片值[{}]", availableTargetNames, shardingValue);
        throw new RuntimeException("暂不支持范围查询");
    }

    @Override
    public Properties getProps() {
        return this.props;
    }

    @Override
    public void init(Properties props) {
        this.props = props;
    }
}
