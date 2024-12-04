package com.lqk.shardingjdbc.dao.entity.sharding;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author liqiankun
 * @date 2024/12/2 10:08
 * @description
 **/
@Setter
@Getter
public class ShardingRoute implements Serializable {

    private static final long serialVersionUID = 1037535337972061207L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 公司ID: 可能为空
     */
    private Long courseId;

    /**
     * 路由字段名
     */
    private String columnName;

    /**
     * 路由字段值
     */
    private String columnValue;

    /**
     * 逻辑表名
     */
    private String logicTable;

    /**
     * 真实表名
     */
    private String realTable;

    /**
     * 新增时间
     */
    private LocalDateTime createTime;
}

