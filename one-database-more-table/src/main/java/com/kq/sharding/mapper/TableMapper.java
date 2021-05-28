package com.kq.sharding.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


/**
 * @author kq
 * @date 2021-05-28 15:25
 * @since 2020-0630
 */
@Mapper
public interface TableMapper {

    @Select(" show tables ")
    public List<Map<String,Object>> getTables();

}
