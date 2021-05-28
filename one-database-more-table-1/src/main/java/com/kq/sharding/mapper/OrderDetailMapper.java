package com.kq.sharding.mapper;

import com.kq.sharding.entity.Order;
import com.kq.sharding.entity.OrderDetail;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * @author kq
 * @date 2021-05-27 8:47
 * @since 2020-0630
 */
@Mapper
public interface OrderDetailMapper {


    @Delete("delete from t_order_detail")
    public void deleteAll();


    @Select("select id,order_id userId,num from t_order_detail")
    public List<Order> getOrderDetailList();


    @Insert("insert into t_order_detail(id,order_id,inventory_id,unit_price,price,num,sale_date,create_time) values(#{id},#{orderId},#{inventoryId},#{unitPrice},#{price},#{num},#{saleDate},#{createTime})")
    void addOrderDetail(OrderDetail o);

}
