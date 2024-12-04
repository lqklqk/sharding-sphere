package com.lqk.shardingjdbc.dao.mapper;

import com.lqk.shardingjdbc.dao.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ：楼兰
 * @date ：Created in 2020/11/12
 * @description:
 **/
@Repository
public interface UserMapper {
    int insert(User user);

    List<User> selectByPassword(@Param("password") String password);
}
