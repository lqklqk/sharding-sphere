package com.lqk.shardingjdbc.dao.entity.sharding;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author liqiankun
 * @date 2024/12/2 13:24
 * @description 分表路由: 真实表分配个数
 **/
@Setter
@Getter
public class ShardingRealTableCount implements Serializable {

    private static final long serialVersionUID = 2529203425793461739L;
    /**
     * 逻辑表名
     */
    private String realTable;

    /**
     * 个数
     */
    private int cnt;
}
