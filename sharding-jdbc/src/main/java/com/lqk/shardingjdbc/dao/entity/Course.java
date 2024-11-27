package com.lqk.shardingjdbc.dao.entity;

import lombok.Data;

/**
 * @author liqiankun
 * @date 2024/11/22 9:48
 * @description 课程表
 **/
@Data
public class Course {
    private Long cid;

    private String cname;

    private Long userId;

    private String cstatus;
}
