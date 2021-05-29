package com.kq.sharding.service;


/**
 * @author kq
 * @date 2021-05-29 13:54
 * @since 2020-0630
 */
public interface TableService {


    /** 注意DDL是没有返回值的，即数据库被影响的行数为0
     * 创建订单表逻辑
     */
    public int createSaleOrderLogic();

}
