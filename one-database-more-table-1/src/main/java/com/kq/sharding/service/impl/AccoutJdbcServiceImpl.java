package com.kq.sharding.service.impl;

import com.kq.sharding.dao.AccountDao;
import com.kq.sharding.entity.Account;
import com.kq.sharding.mapper.TableMapper;
import com.kq.sharding.service.AccoutJdbcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * @author kq
 * @date 2021-06-07 18:13
 * @since 2020-0630
 */
@Service
public class AccoutJdbcServiceImpl implements AccoutJdbcService {

    @Autowired
    private AccountDao accountDao;

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

        accountDao.addAccount(jdbcTemplatea,a);
        accountDao.addAccount(jdbcTemplateb,b);
        accountDao.addAccount(jdbcTemplatec,c);


        accountDao.list(jdbcTemplatea,0);
        accountDao.list(jdbcTemplateb,1);
        accountDao.list(jdbcTemplatec,2);


    }
}
