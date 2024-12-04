package com.lqk.shardingjdbc;

import com.google.common.collect.Lists;
import com.lqk.shardingjdbc.dao.entity.Course;
import com.lqk.shardingjdbc.dao.entity.User;
import com.lqk.shardingjdbc.dao.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author liqiankun
 * @date 2024/12/2 16:53
 * @description
 **/
@Slf4j
@SpringBootTest
public class UserTest {
    @Autowired
    private UserMapper userMapper;
    @Test
    public void addUser(){
        log.info("开始插入数据");
        for(int i = 0; i < 10 ; i++){
            User user = new User();
            user.setUsername("lqk"+i);
            user.setPassword("123456LL"+i);
            user.setUserstatus("1");
            user.setAge(10 + i);
            user.setSex("F");
            userMapper.insert(user);
        }
    }

    @Test
    public void queryUser(){
        List<User> users = userMapper.selectByPassword("123456LL9");
        users.forEach(System.out::println);
    }
}
