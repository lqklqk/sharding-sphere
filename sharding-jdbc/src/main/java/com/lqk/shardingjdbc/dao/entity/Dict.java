package com.lqk.shardingjdbc.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author ：楼兰
 * @date ：Created in 2020/11/12
 * @description:
 **/
@Data
@TableName("t_dict")
public class Dict {
    private Long dictId;
    private String ustatus;
    private String uvalue;
}
