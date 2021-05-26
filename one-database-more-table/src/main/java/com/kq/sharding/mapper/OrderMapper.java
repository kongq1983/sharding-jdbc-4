package com.kq.sharding.mapper;

import com.kq.sharding.entity.Order;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

/**
 * AccountMapper
 *
 * @author1 kq
 * @date 2019-10-12
 */
@Mapper
public interface OrderMapper {

    @Select("select id,user_id userId,code,sale_date saleDate,create_time createTime from t_order limit 20")
    public List<Order> getOrderList();

    @Select("select id,user_id userId,code,sale_date saleDate,create_time createTime from t_order where sale_date> STR_TO_DATE('2021-07-01 10:20:30','%Y-%m-%d') and sale_date< STR_TO_DATE('2021-09-01 10:20:30','%Y-%m-%d')")
    public List<Order> getOrderListBySaleDate();

    @Select("select id,user_id userId,code,sale_date saleDate,create_time createTime from t_order where sale_date> #{startDate} and sale_date< #{endDate}")
    public List<Order> getOrderListBySaleDate1(@Param("startDate") Date startDate, @Param("endDate")Date endDate);

    @Select("select id,user_id userId,code,sale_date saleDate,create_time createTime from t_order where sale_date between #{startDate} and #{endDate}")
    public List<Order> getOrderListBySaleDateBetween(@Param("startDate") Date startDate, @Param("endDate")Date endDate);


    @Insert("insert into t_order(id,user_id,code,sale_date,create_time) values(#{id},#{userId},#{code},#{saleDate},#{createTime})")
    void addOrder(Order o);

//    @Insert("insert into t_order(username,phone,createTime) values(#{username},#{phone},#{createTime})")
//    void addAccountNoId(Order o);

    @Select("select id,user_id userId,code,sale_date saleDate,create_time createTime from t_order where id=#{id}")
    public Order getOrder(String id);

    @Select("select id,user_id userId,code,sale_date saleDate,create_time createTime from t_order_a where id=#{id}")
    public Order getOrderById(String id);

    @Delete("delete from t_order")
    public void deleteAll();

}
