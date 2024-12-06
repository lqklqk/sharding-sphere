package com.lqk.shardingjdbc;

import com.lqk.shardingjdbc.dao.entity.User;
import com.lqk.shardingjdbc.dao.entity.UserCourseInfo;
import com.lqk.shardingjdbc.dao.mapper.UserCourseInfoMapper;
import com.lqk.shardingjdbc.dao.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

/**
 * @author liqiankun
 * @date 2024/12/6 15:50
 * @description
 **/
@Slf4j
@SpringBootTest
public class UserCourseInfoTest {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserCourseInfoMapper userCourseInfoMapper;
    @Test
    public void addUserCourserInfo(){
        log.info("开始插入数据");
        for(int i = 0; i < 10 ; i++){
            String userId = UUID.randomUUID().toString();
            User user = new User();
            user.setUserid(userId);
            user.setUsername("lqk"+i);
            user.setPassword("123456LL"+i);
            user.setUserstatus("1");
            user.setAge(10 + i);
            user.setSex("F");
            userMapper.insertExistId(user);
            for (int j = 0; j < 5; j++) {
                UserCourseInfo userCourseInfo = new UserCourseInfo();
                userCourseInfo.setUserid(userId);
                userCourseInfo.setCourseid(10000L+j);
                userCourseInfoMapper.insert(userCourseInfo);
            }
        }
    }

    @Test
    public void queryUserCourse(){
        List<UserCourseInfo> userCourseInfos = userCourseInfoMapper.selectUserCourse();
        userCourseInfos.forEach(System.out::println);
    }
}
