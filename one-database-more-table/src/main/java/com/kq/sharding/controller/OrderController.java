package com.kq.sharding.controller;

import com.kq.sharding.entity.Order;
import com.kq.sharding.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;


/**
 * @author kq
 * @date 2021-05-25 16:46
 * @since 2020-0630
 */

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderMapper orderMapper;

    @RequestMapping("/{id}")
    public Order list(@PathVariable("id") String id){
        return orderMapper.getOrder(id);
    }

    @RequestMapping("/list")
    public List<Order> list(){
        return orderMapper.getOrderList();
    }

    private AtomicLong atomicLong = new AtomicLong(0);
    private static final String shortDateFormat = "yyyy-MM-dd";


    @RequestMapping("/add")
    public String add(Order account){

        String dates[]  = {"2021-05-25","2021-06-10","2021-07-12","2021-08-08","2021-09-09","2021-10-10"};


        for(int i=0;i<dates.length;i++) {
            long id = atomicLong.incrementAndGet();
            Order o = new Order();
            o.setId(String.valueOf(id));
            o.setCode(String.valueOf(id));
            o.setCreateTime(new Date());
            o.setSaleDate(stringToDate(dates[i]));
            o.setUserId(String.valueOf(id));
            orderMapper.addOrder(o);
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


}