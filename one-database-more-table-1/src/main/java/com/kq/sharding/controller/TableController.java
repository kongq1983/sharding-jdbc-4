package com.kq.sharding.controller;

import com.kq.sharding.mapper.TableMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.shardingjdbc.jdbc.core.datasource.ShardingDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author kq
 * @date 2021-05-28 15:30
 * @since 2020-0630
 */

@Slf4j
@RestController
@RequestMapping("/table")
public class TableController {

    @Autowired
    private TableMapper tableMapper;

    @Autowired
    private ShardingDataSource shardingDataSource;


    @RequestMapping("/info")
    public String info(){
        System.out.println(shardingDataSource);
        return "pk";
    }

    @RequestMapping("/list")
    public List<String> list(){
        return tableMapper.getTables();
    }


}
