package com.kq.sharding.mapper;

import com.kq.sharding.entity.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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


    @Insert("insert into t_order(id,user_id,code,sale_date,create_time) values(#{id},#{userId},#{code},#{saleDate},#{createTime})")
    void addOrder(Order o);

//    @Insert("insert into t_order(username,phone,createTime) values(#{username},#{phone},#{createTime})")
//    void addAccountNoId(Order o);

    @Select("select id,user_id userId,code,sale_date saleDate,create_time createTime from t_order where id=#{id}")
    public Order getOrder(String id);

}
