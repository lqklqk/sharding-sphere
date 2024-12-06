package com.lqk.shardingjdbc.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lqk.shardingjdbc.dao.entity.Dict;
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
public interface DictMapper {
    int insert(Dict dict);

    List<Dict> selectByDictKey(@Param("dictKey") String dictKey);
}
