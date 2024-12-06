package com.lqk.shardingjdbc.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author ：lqk
 * @date ：Created in 2020/11/12
 * @description: 字典表实体
 **/
@Data
public class Dict {
    private Long dictId;
    private String dictKey;
    private String dictVal;
}
