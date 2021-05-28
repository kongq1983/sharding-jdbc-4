package com.kq.sharding.demo1.controller;


import com.kq.sharding.demo1.entity.Account;
import com.kq.sharding.demo1.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * AccountController
 *
 * @author kq
 * @date 2019-10-11
 */
@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountMapper accountDao;


    @RequestMapping("/{id}")
    public Account list(@PathVariable("id") Long id){
        return accountDao.getAccount(id);
    }

    @RequestMapping("/list")
    public List<Account> list(){
        return accountDao.getAccountList();
    }


    @RequestMapping("/add")
    public String add(Account account){


//        if(account.getId().intValue()<10000) {
//            account.setCreateTime(DateUtil.getDate(2018));
//        } else {
//            account.setCreateTime(new Date());
//        }
        // 没有插入ID
//        accountDao.addAccountNoId(account);
        accountDao.addAccount(account);

        return "ok";
    }


}
