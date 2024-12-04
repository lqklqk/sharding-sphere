package com.lqk.shardingjdbc.dao.query;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author liqiankun
 * @date 2024/12/2 13:51
 * @description
 **/
@Setter
@Getter
public class ShardingRouteQuery implements Serializable {

    private static final long serialVersionUID = 684822718580677575L;

    /**
     * 逻辑表名
     */
    private String logicTable;

    /**
     * 分表键值
     */
    private String columnValue;

    /**
     * 分表键值
     */
    private Collection<String> columnValues;
}
