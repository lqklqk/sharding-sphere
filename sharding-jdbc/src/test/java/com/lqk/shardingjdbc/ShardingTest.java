package com.lqk.shardingjdbc;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
            c.setUserId(100L);
            c.setCstatus("1");
            courseMapper.insert(c);
        }
    }

    @Test
    public void queryCourse(){

        QueryWrapper wrapper = new QueryWrapper<Course>();
//        wrapper.eq("cid",1508073797789872129L);
//        wrapper.between("cid",1508073797789872129L,1508073803737395201L);
//        wrapper.in("cid",1508073797789872129L,1508073803468959745L);
//        wrapper.between("user_id",99L,101L);

        HintManager hintManager = HintManager.getInstance();
        hintManager.addTableShardingValue("course","1");

        for (Object o : courseMapper.selectList(wrapper)) {
            System.out.println(o);
        }
    }
//
//
//     @Test
//     public void addDict(){
//         Dict dict = new Dict();
//         dict.setUstatus("1");
//         dict.setUvalue("正常");
//         dictMapper.insert(dict);
//
//         Dict dict2 = new Dict();
//         dict2.setUstatus("2");
//         dict2.setUvalue("异常");
//         dictMapper.insert(dict2);
//     }
//
//     @Test
//     public void addUser(){
//         for (int i = 0; i < 10; i++) {
//             User u = new User();
//             u.setUsername("user");
//             u.setUstatus(""+(i%2+1));
//             userMapper.insert(u);
//         }
//     }
//
//     @Test
//     public void queryDict(){
//         QueryWrapper<Dict> wrapper = new QueryWrapper<Dict>();
// //        wrapper.eq("ustatus", "1");
//         List<Dict> dicts = dictMapper.selectList(wrapper);
//         dicts.forEach(dict -> System.out.println(dict));
//     }
//
//     @Test
//     public void queryUserStatus(){
//         List<User> users = userMapper.queryUserStatus();
//         for(User user : users){
//             System.out.println(user);
//         }
//     }

}
