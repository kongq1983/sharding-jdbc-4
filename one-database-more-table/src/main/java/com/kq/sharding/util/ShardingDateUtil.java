package com.kq.sharding.util;

import com.google.common.collect.Lists;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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


    public static Date stringToDate(String str) {
        if (str == null) {
            return null;
        }
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date=sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static List<Date> getBetweenDates(Date startDate, Date endDate) {

        startDate = dealStartDate(startDate);
        endDate = dealStartDate(endDate);


        int index = 1;

        List<Date> newList = Lists.newArrayList();
        newList.add(startDate);

        //同一天
        if(startDate.getTime()==endDate.getTime()) {
            return newList;
        }

        Date newDate = getAddOneDay(startDate);

        while (newDate.getTime() < endDate.getTime()) {
            if (index > 500)
                break; // 防止死循环
            newList.add(newDate);
            newDate = getAddOneDay(newDate);
            index++;
        }

        newList.add(endDate);

        return newList;

    }

    public static Date dealStartDate(Date date) {
        if(date==null) return null;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);

        return calendar.getTime();

    }

    /**
     * 处理结束时间
     * @param date
     * @return
     */
    public static Date dealEndDate(Date date) {
        if(date==null) return null;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND,59);

        return calendar.getTime();

    }

    /**
     * 加1天
     *
     * @param date
     * @return
     */
    public static Date getAddOneDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + 1);

        return c.getTime();
    }


    public static void main(String[] args) {
        System.out.println(getOrderTableName(new Date()));
    }


}
