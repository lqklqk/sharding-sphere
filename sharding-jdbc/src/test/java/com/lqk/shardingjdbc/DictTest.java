package com.lqk.shardingjdbc;

import com.lqk.shardingjdbc.dao.entity.Dict;
import com.lqk.shardingjdbc.dao.entity.User;
import com.lqk.shardingjdbc.dao.mapper.DictMapper;
import com.lqk.shardingjdbc.dao.mapper.UserSplitMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author liqiankun
 * @date 2024/12/6 14:39
 * @description
 **/
@Slf4j
@SpringBootTest
public class DictTest {
    @Autowired
    private DictMapper dictMapper;
    @Test
    public void addDict(){
        log.info("开始插入数据");
        Dict dict = new Dict();
        dict.setDictKey("F");
        dict.setDictVal("女");
        dictMapper.insert(dict);
        Dict dict2 = new Dict();
        dict2.setDictKey("M");
        dict2.setDictVal("男");
        dictMapper.insert(dict2);
    }

    @Test
    public void queryDict(){
        List<Dict> dicts = dictMapper.selectByDictKey("F");
        dicts.forEach(System.out::println);
    }
}
