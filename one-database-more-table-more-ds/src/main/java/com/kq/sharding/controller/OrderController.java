package com.kq.sharding.controller;

import com.kq.sharding.entity.DtoPage;
import com.kq.sharding.entity.Order;
import com.kq.sharding.entity.OrderDetail;
import com.kq.sharding.mapper.OrderDetailMapper;
import com.kq.sharding.mapper.OrderMapper;
import com.kq.sharding.service.OrderService;
import com.kq.sharding.util.ShardingDateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.shardingjdbc.jdbc.core.datasource.ShardingDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;


/**
 * @author kq
 * @date 2021-05-25 16:46
 * @since 2020-0630
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @RequestMapping("/delete")
    public String deleteAll(){
        orderMapper.deleteAll();
        orderDetailMapper.deleteAll();
        return "ok";
    }

    @RequestMapping("/{id}")
    public Order list(@PathVariable("id") String id){
        return orderMapper.getOrder(id);
    }

    @RequestMapping("/a/{id}")
    public Order lista(@PathVariable("id") String id){
        return orderMapper.getOrderById(id);
    }

    @RequestMapping("/list")
    public List<Order> list(){
        return orderMapper.getOrderList();
    }

    /**
     * 不会分片
     * @return
     */
    @RequestMapping("/list1")
    public List<Order> list1(){
        return orderMapper.getOrderListBySaleDate();
    }

    @RequestMapping("/list2")
    public List<Order> list2(){
        Date startDate = ShardingDateUtil.stringToDate("2021-07-01 00:00:00");
        Date endDate = ShardingDateUtil.stringToDate("2021-09-01 00:00:00");

        log.info("list2 startDate={},endDate={}",startDate,endDate);

        return orderMapper.getOrderListBySaleDate1(startDate,endDate);
    }


    @RequestMapping("/listb")
    public List<Order> listBetween(){
        Date startDate = ShardingDateUtil.stringToDate("2021-07-01 00:00:00");
        Date endDate = ShardingDateUtil.stringToDate("2021-09-01 00:00:00");

        log.info("listBetween startDate={},endDate={}",startDate,endDate);

        return orderMapper.getOrderListBySaleDateBetween(startDate,endDate);
    }

    @RequestMapping("/listd1")
    public List<Map<String,Object>> getOrderAndDetailList(){
//        Date startDate = ShardingDateUtil.stringToDate("2021-07-01 00:00:00");
//        Date endDate = ShardingDateUtil.stringToDate("2021-09-01 00:00:00");
//
//        log.info("listBetween startDate={},endDate={}",startDate,endDate);

        return orderMapper.getOrderAndDetailList1();
    }

    @RequestMapping("/listd2")
    public List<Map<String,Object>> getOrderAndDetailList2(){
//        Date startDate = ShardingDateUtil.stringToDate("2021-07-01 00:00:00");
//        Date endDate = ShardingDateUtil.stringToDate("2021-09-01 00:00:00");
//
//        log.info("listBetween startDate={},endDate={}",startDate,endDate);

        return orderMapper.getOrderAndDetailList2();
    }

    @RequestMapping("/listd3")
    public List<Map<String,Object>> getOrderAndDetailList3(){
        return orderMapper.getOrderAndDetailList3();
    }

    @RequestMapping("/listd4")
    public List<Map<String,Object>> getOrderAndDetailList4() {
        return orderMapper.getOrderAndDetailList4();
    }

    /**
     * 根据id分片 查找
     * @param curPage
     * @return
     */
    @RequestMapping("/listpa/{curPage}")
    public List<Map<String,Object>> getOrderAndDetailListPagea(@PathVariable("curPage") Integer curPage){
        DtoPage dto = new DtoPage();
        if(curPage==null){
            curPage=1;
        }
        dto.setPageNow(curPage);

        Date startDate = ShardingDateUtil.stringToDate("2021-07-01 00:00:00");
        Date endDate = ShardingDateUtil.stringToDate("2021-09-01 00:00:00");

        dto.setStartDate(startDate);
        dto.setEndDate(endDate);

        return orderService.getOrders1(dto);
    }

    /**
     * 根据销售日期分片查找
     * @param curPage
     * @return
     */
    @RequestMapping("/listp/{curPage}")
    public List<Map<String,Object>> getOrderAndDetailListPage(@PathVariable("curPage") Integer curPage){
        DtoPage dto = new DtoPage();
        if(curPage==null){
            curPage=1;
        }
        dto.setPageNow(curPage);

        Date startDate = ShardingDateUtil.stringToDate("2021-07-01 00:00:00");
        Date endDate = ShardingDateUtil.stringToDate("2021-09-01 00:00:00");

        dto.setStartDate(startDate);
        dto.setEndDate(endDate);

        return orderService.getOrders(dto);
    }

    private AtomicLong atomicLong = new AtomicLong(0);
    private static final String shortDateFormat = "yyyy-MM-dd";


    @RequestMapping("/add")
    public String add(Order account){

        String dates[]  = {"2021-05-25","2021-06-10","2021-07-12","2021-08-08","2021-09-09","2021-10-10"};


        for(int i=0;i<dates.length;i++) {
            long id = atomicLong.incrementAndGet();
            Order o = new Order();
            o.setId(dates[i].replaceAll("-","")+id);
            o.setCode(String.valueOf(id));
            o.setCreateTime(new Date());
            o.setSaleDate(stringToDate(dates[i]));
            o.setUserId(String.valueOf(id));
            orderMapper.addOrder(o);

            OrderDetail od = new OrderDetail();
            od.setId(o.getId()+"detail");
            od.setOrderId(o.getId());
            od.setInventoryId(String.valueOf(i));
            od.setCreateTime(o.getCreateTime());
            od.setNum(i+1);
            od.setSaleDate(o.getSaleDate());

            orderDetailMapper.addOrderDetail(od);

        }



        return "ok";
    }


    public static Date stringToDate(String date) {
        SimpleDateFormat f = new SimpleDateFormat(shortDateFormat);
        try {
            return f.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public AtomicLong getAtomicLong(){
        return this.atomicLong;
    }


    @RequestMapping("/listaa")
    public List<Map<String,Object>> orderListA() {


        String sql = "select * from t_order_a o where o.id = ?"; //

        Object[] os = {"20210806102030111111111111111111111111"};
        List<Map<String, Object>> list = getShardingJdbcTemplate().queryForList(sql,os);

        return list;
    }

    @Autowired
    ShardingDataSource shardingDataSource;

    public JdbcTemplate getShardingJdbcTemplate() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(shardingDataSource);
        return jdbcTemplate;
    }

}
