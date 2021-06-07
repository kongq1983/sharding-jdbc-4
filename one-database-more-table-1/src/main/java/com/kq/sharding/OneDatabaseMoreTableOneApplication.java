package com.kq.sharding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class OneDatabaseMoreTableOneApplication {

    public static void main(String[] args) {
        SpringApplication.run(OneDatabaseMoreTableOneApplication.class, args);
    }

}
