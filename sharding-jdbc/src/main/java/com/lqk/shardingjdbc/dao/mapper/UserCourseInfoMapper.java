package com.lqk.shardingjdbc.dao.mapper;

import com.lqk.shardingjdbc.dao.entity.UserCourseInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author liqiankun
 * @date 2024/12/6 15:17
 * @description
 **/
@Repository
public interface UserCourseInfoMapper {
    void insert(UserCourseInfo userCourseInfo);
    List<UserCourseInfo> selectUserCourse();
}
