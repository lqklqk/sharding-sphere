package com.lqk.shardingjdbc.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author ：楼兰
 * @date ：Created in 2020/11/12
 * @description:
 **/
@Data
@TableName("t_user")
public class User {
    private Long userId;
    private String username;
    private String ustatus;
    private int uage;

}
