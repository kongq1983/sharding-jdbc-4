package com.kq.sharding.demo1.controller;


import com.kq.sharding.demo1.entity.Dict;
import com.kq.sharding.demo1.mapper.DictMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * DictController
 *
 * @author kq
 * @date 2019-10-11
 */
@RestController
@RequestMapping("/dict")
public class DictController {

    @Autowired
    private DictMapper dictDao;


    @RequestMapping("/{id}")
    public Dict list(@PathVariable("id") Long id){
        return dictDao.getDict(id);
    }

    @RequestMapping("/list")
    public List<Dict> list(){
        return dictDao.getDictList();
    }


    @RequestMapping("/add")
    public String add(Dict Dict){


//        if(Dict.getId().intValue()<10000) {
//            Dict.setCreateTime(DateUtil.getDate(2018));
//        } else {
//            Dict.setCreateTime(new Date());
//        }
        // 没有插入ID
//        DictDao.addDictNoId(Dict);
        dictDao.addDict(Dict);

        return "ok";
    }


}
