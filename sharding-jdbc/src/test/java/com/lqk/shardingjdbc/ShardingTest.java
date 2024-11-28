package com.lqk.shardingjdbc;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.lqk.shardingjdbc.dao.entity.Course;
import com.lqk.shardingjdbc.dao.entity.Dict;
import com.lqk.shardingjdbc.dao.mapper.CourseMapper;
import com.lqk.shardingjdbc.dao.mapper.DictMapper;
import com.lqk.shardingjdbc.dao.mapper.UserMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.infra.hint.HintManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author roy
 * @date 2022/3/27
 * @desc
 */
@Slf4j
@SpringBootTest
public class ShardingTest {

    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private DictMapper dictMapper;
    @Autowired
    private UserMapper userMapper;
    @Test
    public void addCcourse(){
        log.info("开始插入数据");
        for(int i = 0; i < 10 ; i++){
            Course c = new Course();
            c.setCname("java");
            Long userId = (long)(1000 + (i % 3));
            c.setUserId(userId);
            c.setCstatus("1");
            courseMapper.insert(c);
        }
    }

    @Test
    public void queryCourse(){
        Course course = courseMapper.selectById(1068568289690517504L);
        System.out.println(course);
        System.out.println("----------------------------------");
        List<Course> courses = courseMapper.selectBatchById(Lists.newArrayList(1068568289690517504L, 1068568290437103616L, 1068568290453880833L, 1068568290491629569L), 0L);
        courses.forEach(System.out::println);
    }

    @Test
    public void queryCourse2(){
        List<Course> courses = courseMapper.selectRangeById(1068568290378383361L, 1068568290437103616L, 1001L);
        courses.forEach(System.out::println);
    }

    @Test
    public void queryCourse3(){
        List<Course> courses = courseMapper.selectBatchById(Lists.newArrayList(1068568289690517504L, 1068568290403549184L), 1002L);
        courses.forEach(System.out::println);
    }

    @Test
    public void queryCourseOddCid(){
        List<Course> courses = courseMapper.selectByOddCid();
        courses.forEach(System.out::println);
    }

    @Test
    public void queryByHint(){
        // 强制只查询course_1
        // HintManager实现了AutoCloseable接口，会自动调用hintManager.close();清除ThreadLocal，释放内存
        try(HintManager hintManager = HintManager.getInstance()) {
            // 注意：addDatabaseShardingValue\setDatabaseShardingValue这两个参数是强制分库
            hintManager.addTableShardingValue("course","1");

            for (Object o : courseMapper.selectByOddCid()) {
                System.out.println(o);
            }
        }
    }
}
