package com.kq.sharding.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kq.sharding.entity.DtoPage;
import com.kq.sharding.mapper.OrderMapper;
import com.kq.sharding.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author kq
 * @date 2021-05-27 11:20
 * @since 2020-0630
 */

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

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

}
