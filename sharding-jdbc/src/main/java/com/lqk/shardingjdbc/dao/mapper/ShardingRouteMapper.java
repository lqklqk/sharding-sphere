package com.lqk.shardingjdbc.dao.mapper;

import com.lqk.shardingjdbc.dao.entity.sharding.ShardingRealTableCount;
import com.lqk.shardingjdbc.dao.entity.sharding.ShardingRoute;
import com.lqk.shardingjdbc.dao.query.ShardingRouteQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author liqiankun
 * @date 2024/12/2 11:24
 * @description 分表路由
 **/
@Repository
public interface ShardingRouteMapper {
    /**
     * 列表查询
     *
     * @param query 查询对象
     * @return 列表
     */
    List<ShardingRoute> list(ShardingRouteQuery query);

    /**
     * 获取所有路由
     *
     * @return 所有路由
     */
    List<ShardingRoute> getAll();

    /**
     * 新增
     *
     * @param route 路由
     */
    void insert(ShardingRoute route);

    /**
     * 新增
     *
     * @param list 路由s
     */
    void batchInsert(List<ShardingRoute> list);

    /**
     * 根据key查询路由
     *
     * @param logicTable 逻辑表
     * @param columnName 路由字段名
     * @param columnValue 路由字段值
     * @return 路由
     */
    ShardingRoute get(@Param("logicTable") String logicTable,
                      @Param("columnName") String columnName,
                      @Param("columnValue") String columnValue);

    /**
     * 获取相同公司的一条记录
     *
     * @param logicTable 逻辑表
     * @param companyId 公司ID
     * @return 相同公司的一条记录
     */
    ShardingRoute getOneSameCompanyRoute(@Param("logicTable") String logicTable,
                                         @Param("companyId") Long companyId);

    /**
     * 查询真实表分配个数
     * 返回结果根据字段cnt升序排列
     *
     * @param logicTable 逻辑表
     * @return 真实表分配个数
     */
    List<ShardingRealTableCount> countRealTable(@Param("logicTable") String logicTable);

    // /**
    //  * 查询以核算主体分表的路由
    //  *
    //  * @param query 查询对象
    //  * @return 路由
    //  */
    // List<ShardingRoute> listByAccountOrg(AccountOrgRouteQuery query);
}
