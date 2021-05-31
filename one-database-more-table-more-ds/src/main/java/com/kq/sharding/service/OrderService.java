package com.kq.sharding.service;

import com.github.pagehelper.Page;
import com.kq.sharding.entity.Account;
import com.kq.sharding.entity.DtoPage;
import com.kq.sharding.entity.Order;

/**
 * @author kq
 * @date 2021-05-27 11:19
 * @since 2020-0630
 */

public interface OrderService {


    public Page getOrders(DtoPage dto);

    public Page getOrders1(DtoPage dto);

    public void addOrderAndAccount(Order order,String key);


}
