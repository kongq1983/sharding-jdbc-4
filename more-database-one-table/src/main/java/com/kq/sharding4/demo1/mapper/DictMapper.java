package com.kq.sharding4.demo1.mapper;

import com.kq.sharding4.demo1.entity.Dict;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DictMapper {

    @Select("select id,type,name,code,status from Dict limit 50")
    public List<Dict> getDictList();


    @Insert("insert into Dict(id,type,name,code,status) values(#{id},#{type},#{name},#{code},#{status})")
    void addDict(Dict o);

    @Insert("insert into Dict(type,name,code,status) values(#{type},#{name},#{code},#{status})")
    void addDictNoId(Dict o);

    @Select("select id,type,name,code,status from Dict where id=#{id}")
    public Dict getDict(Long id);

}
