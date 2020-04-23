package com.kq.sharding4.demo1.mapper;

import com.kq.sharding4.demo1.entity.Account;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * AccountMapper
 *
 * @author1 kq
 * @date 2019-10-12
 */
@Mapper
public interface AccountMapper {

    @Select("select id,username,phone,province,createTime from account limit 20")
    public List<Account> getAccountList();


    @Insert("insert into account(id,username,phone,createTime) values(#{id},#{username},#{phone},#{createTime})")
    void addAccount(Account o);

    @Insert("insert into account(username,phone,createTime) values(#{username},#{phone},#{createTime})")
    void addAccountNoId(Account o);

    @Select("select id,username,phone,province,createTime from account where id=#{id}")
    public Account getAccount(Long id);

}
