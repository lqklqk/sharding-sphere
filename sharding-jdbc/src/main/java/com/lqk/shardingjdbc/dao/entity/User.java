package com.lqk.shardingjdbc.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author ：楼兰
 * @date ：Created in 2020/11/12
 * @description:
 **/
@Data
public class User {
    private String userid;
    private String username;
    private String password;
    private String passwordCipher;
    private String userstatus;
    private int age;
    private String sex;
}
