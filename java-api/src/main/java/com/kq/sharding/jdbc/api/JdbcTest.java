package com.kq.sharding.jdbc.api;

import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcTest {

    private static  APIConfiguration configuration = new APIConfiguration();

    public static void main(String[] args) throws Exception{
        JdbcTemplate jdbcTemplate = new JdbcTemplate(configuration.getShardingDataSource());
    }


}
