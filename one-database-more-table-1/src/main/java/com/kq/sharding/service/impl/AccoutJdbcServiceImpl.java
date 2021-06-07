package com.kq.sharding.service.impl;

import com.kq.sharding.dao.AccountDao;
import com.kq.sharding.entity.Account;
import com.kq.sharding.mapper.AccountMapper;
import com.kq.sharding.mapper.TableMapper;
import com.kq.sharding.service.AccoutJdbcService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

/**
 * @author kq
 * @date 2021-06-07 18:13
 * @since 2020-0630
 */
@Slf4j
@Service
public class AccoutJdbcServiceImpl implements AccoutJdbcService {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private TableMapper tableMapper;

    @Override
    public void add() {

        int index = new Random().nextInt(100);

        Account a = new Account();
        a.setUsername("aa"+index);
        a.setPhone("168");

        JdbcTemplate jdbcTemplatea = tableMapper.getNonShardingJdbcTemplate();


        Account b = new Account();
        b.setUsername("bb"+index);
        b.setPhone("188");

        JdbcTemplate jdbcTemplateb = tableMapper.getNonShardingJdbcTemplate();

        Account c = new Account();
        c.setUsername("cc"+index);
        c.setPhone("188");

        JdbcTemplate jdbcTemplatec = tableMapper.getShardingJdbcTemplate();

        Account d = new Account();
        d.setUsername("mybatis"+index);
        d.setPhone("188");

        List<Account> mybatisList = accountMapper.getAccountList1();
        log.info("mybatisList={}",mybatisList);

        this.accountMapper.addAccountNoId(d);
        accountDao.addAccount(jdbcTemplatea,a);
        accountDao.addAccount(jdbcTemplateb,b);
        accountDao.addAccount(jdbcTemplatec,c);

        accountDao.list(jdbcTemplatea,0);
        accountDao.list(jdbcTemplateb,1);
        accountDao.list(jdbcTemplatec,2);
        List<Account> mybatisList1 = accountMapper.getAccountList1();
        log.info("mybatisList1={}",mybatisList);


    }

    @Transactional
    @Override
    public void add1() {
        // jdbc调用存储过程
        JdbcTemplate jdbcTemplatea = tableMapper.getNonShardingJdbcTemplate();
        String sql = "call accountinit(?)";
        jdbcTemplatea.update(sql,3);

//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        // mybatis
        List<Account> mybatisList1 = accountMapper.getAccountList1();
        log.info("mybatisList1={}",mybatisList1);
    }

}
