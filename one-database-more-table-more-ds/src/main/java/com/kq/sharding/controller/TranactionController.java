package com.kq.sharding.controller;

import com.kq.sharding.entity.Account;
import com.kq.sharding.entity.Order;
import com.kq.sharding.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author kq
 * @date 2021-05-31 19:12
 * @since 2020-0630
 */

@RestController
@RequestMapping("/transaction")
public class TranactionController {

    private static final String shortDateFormat = "yyyy-MM-dd";

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderController orderController;


    @RequestMapping("/add")
    public String add(Account account){


//        if(account.getId().intValue()<10000) {
//            account.setCreateTime(DateUtil.getDate(2018));
//        } else {
//            account.setCreateTime(new Date());
//        }
        // 没有插入ID
//        accountDao.addAccountNoId(account);

        String dates[]  = {"2021-05-25","2021-06-10","2021-07-12","2021-08-08","2021-09-09","2021-10-10"};

        long id = orderController.getAtomicLong().incrementAndGet();
        Order o = new Order();
        o.setId(dates[0].replaceAll("-","")+id);
        o.setCode(String.valueOf(id));
        o.setCreateTime(new Date());
        o.setSaleDate(stringToDate(dates[0]));
        o.setUserId(String.valueOf(id));

        orderService.addOrderAndAccount(o,"");

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
