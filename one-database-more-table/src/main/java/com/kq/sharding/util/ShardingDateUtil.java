package com.kq.sharding.util;

import java.util.Calendar;
import java.util.Date;

/**
 * @author kq
 * @date 2021-05-25 16:11
 * @since 2020-0630
 */
public class ShardingDateUtil {

    public static String getOrderTableName(Date date) {

        if(date==null) {
            return null;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;


        return year+""+getFormatMonth(month);


    }

    private static String getFormatMonth(int month) {
        if(month>9) {
            return String.valueOf(month);
        }

        return "0"+month;

    }

    public static void main(String[] args) {
        System.out.println(getOrderTableName(new Date()));
    }


}
