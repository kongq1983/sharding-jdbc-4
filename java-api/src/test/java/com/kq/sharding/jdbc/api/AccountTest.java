package com.kq.sharding.jdbc.api;

import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

public class AccountTest {

    APIConfiguration configuration = new APIConfiguration();
    JdbcTemplate jdbcTemplate = null;

    @Before
    public void before() throws Exception{
        jdbcTemplate = new JdbcTemplate(configuration.getShardingDataSource());
    }


    @Test
    public void testLoadAll() throws Exception{

        String sql = "select id,username,phone,province,createTime from account";
        List<Map<String, Object>> list =jdbcTemplate.queryForList(sql);

        System.out.println("list="+list);

    }

    @Test
    public void addAccount() throws Exception{
        Long id = 6l;
        String username = "k"+id;
        String phone = "13512341234";
        String sql = "insert into account(id,username,phone) values(?,?,?)";
        jdbcTemplate.update(sql,id,username,phone);

    }

}
