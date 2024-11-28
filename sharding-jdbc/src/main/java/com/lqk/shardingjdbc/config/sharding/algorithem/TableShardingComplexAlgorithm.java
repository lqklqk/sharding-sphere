package com.lqk.shardingjdbc.config.sharding.algorithem;

import com.google.common.collect.Range;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingValue;
import org.apache.shardingsphere.sharding.exception.syntax.UnsupportedShardingOperationException;

import java.util.*;

/**
 * @author liqiankun
 * @date 2024/11/28 14:26
 * @description 实现自定义COMPLEX分片策略
 * 声明算法时，ComplexKeysShardingAlgorithm接口可传入一个泛型，这个泛型就是分片键的数据类型。
 * 这个泛型只要实现了Comparable接口即可。
 * 但是官方不建议声明一个明确的泛型出来，建议是在doSharding中再做类型转换。这样是为了防止分片键类型与算法声明的类型不符合。
 **/
public class TableShardingComplexAlgorithm implements ComplexKeysShardingAlgorithm<Long> {

    private static final String SHARDING_COLUMNS_KEY = "sharding-columns";

    private Properties pops;

    private Collection<String> shardingColumns;


    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, ComplexKeysShardingValue<Long> complexKeysShardingValue) {
        /**
         * availableTargetNames：可选的节点名称（如果是分表算法则表示真实表名称）
         * complexKeysShardingValue：分片条件
         *  columnNameAndShardingValuesMap：精确查询条件
         *  columnNameAndRangeValuesMap：范围查询条件
         */
        Collection<Long> cidCol = complexKeysShardingValue.getColumnNameAndShardingValuesMap().get("cid");
        Range<Long> userIdRange = complexKeysShardingValue.getColumnNameAndRangeValuesMap().get("user_id");
        Long lowerEndpoint = userIdRange.lowerEndpoint();
        Long upperEndpoint = userIdRange.upperEndpoint();
        if (lowerEndpoint >= upperEndpoint) {
            throw new UnsupportedShardingOperationException("empty record query", "course");
        } else {
            List<String> result = new ArrayList<>();
            // 逻辑表名称
            String logicTableName = complexKeysShardingValue.getLogicTableName();
            for (Long cid : cidCol) {
                String realTableName = logicTableName + "_" + (cid%2 + 1);
                if (availableTargetNames.contains(realTableName)) {
                    result.add(realTableName);
                }
            }
            return result;
        }
    }

    @Override
    public Properties getProps() {
        return this.pops;
    }

    @Override
    public void init(Properties props) {
        this.pops = props;
        this.shardingColumns = getShardingColumns(props);
    }

    public void setPops(Properties props) {

    }
    private Collection<String> getShardingColumns(Properties props) {
        String shardingColumns = props.getProperty(SHARDING_COLUMNS_KEY, "");
        return shardingColumns.isEmpty() ? Collections.emptyList() : Arrays.asList(shardingColumns.split(","));
    }


}
