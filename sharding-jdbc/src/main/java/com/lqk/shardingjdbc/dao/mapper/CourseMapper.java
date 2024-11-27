package com.lqk.shardingjdbc.dao.mapper;

import com.lqk.shardingjdbc.dao.entity.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author liqiankun
 * @date 2024/11/22 9:51
 * @description
 **/
@Repository
public interface CourseMapper {

    int insert(Course course);

    Course selectById(@Param("cid") Long cid);

    List<Course> selectBatchById(@Param("cids") List<Long> cids);
}
