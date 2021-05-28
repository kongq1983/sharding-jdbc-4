package com.kq.sharding.controller;

import com.kq.sharding.mapper.TableMapper;
import lombok.extern.slf4j.Slf4j;
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


    @RequestMapping("/list")
    public List<Map<String,Object>> list(){
        return tableMapper.getTables();
    }


}
