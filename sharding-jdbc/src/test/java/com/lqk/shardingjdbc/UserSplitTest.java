package com.lqk.shardingjdbc;

import com.lqk.shardingjdbc.dao.entity.User;
import com.lqk.shardingjdbc.dao.mapper.UserMapper;
import com.lqk.shardingjdbc.dao.mapper.UserSplitMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author liqiankun
 * @date 2024/12/5 16:58
 * @description 测试读写分离
 **/
@Slf4j
@SpringBootTest
public class UserSplitTest {
    @Autowired
    private UserSplitMapper userSplitMapper;
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
            userSplitMapper.insert(user);
        }
    }

    @Test
    public void queryUser(){
        List<User> users = userSplitMapper.selectByPassword("123456LL9");
        users.forEach(System.out::println);
    }
}
