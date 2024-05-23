package com.kq.sharding;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@MapperScan("com.kq.sharding.dao")

@EnableTransactionManagement
//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@SpringBootApplication
public class ShardingQuickStarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShardingQuickStarterApplication.class, args);
    }

}
