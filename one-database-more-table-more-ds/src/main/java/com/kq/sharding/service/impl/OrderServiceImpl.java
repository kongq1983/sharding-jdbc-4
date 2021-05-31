package com.kq.sharding.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kq.sharding.entity.Account;
import com.kq.sharding.entity.DtoPage;
import com.kq.sharding.entity.Order;
import com.kq.sharding.mapper.AccountMapper;
import com.kq.sharding.mapper.OrderMapper;
import com.kq.sharding.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author kq
 * @date 2021-05-27 11:20
 * @since 2020-0630
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public Page getOrders(DtoPage dto) {

        PageHelper.startPage(dto.getPageNow(), dto.getPageSize());

        return orderMapper.getOrderAndDetailListForPage(dto.getStartDate(),dto.getEndDate());

    }

    @Override
    public Page getOrders1(DtoPage dto) {

        PageHelper.startPage(dto.getPageNow(), dto.getPageSize());

        return orderMapper.getOrderAndDetailListForPage1(dto.getStartDate(),dto.getEndDate());

    }

    @Transactional
    @Override
    public void addOrderAndAccount(Order order, String key) {

        log.info("========   start add order id={}",order.getId());
        orderMapper.addOrder(order);
        log.info("========   e-n-d add order id={}",order.getId());

        Account account = new Account();
        account.setId(100L);
        log.info("========   start add ACCOUNT id={}",order.getId());
        account.setUsername("hello");
        account.setCreateTime(new Date());
        account.setPhone("12345678901234567890123456789012345678901234567890");

        accountMapper.addAccount(account);
        log.info("========   e-n-d add ACCOUNT id={}",order.getId());



    }


}
