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

    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    /**
     * 注意: 这个方法不能乱改  否则会影响分库 分表
     * @param date
     * @return
     */
    public static String getShardingTableName(Date date) {

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

    /**
     * 添加几个月
     * @param date
     * @param month
     * @return
     */
    public static Date getAddMonth(Date date,int month) {
         Calendar c = Calendar.getInstance();
         c.setTime(date);
         c.add(Calendar.MONTH, month);
         return c.getTime();
    }

    public static String dateToString(Date time) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
            return sdf.format(time);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static List<Date> getBetweenMonths(Date startDate, Date endDate) {

        startDate = dealStartDate(startDate);
        endDate = dealStartDate(endDate);

        int index = 1;

        List<Date> newList = Lists.newArrayList();
        newList.add(startDate);

        //同一天
        if(startDate.getTime()==endDate.getTime()) {
            return newList;
        }

        Date newDate = dealStartDate(getAddMonth(startDate,1));

        while (newDate.getTime() < endDate.getTime()) {
            if (index > 36)
                break; // 防止死循环
            newList.add(newDate);
            newDate = dealStartDate(getAddMonth(newDate,1));
            index++;
        }

//        newList.add(endDate);

        return newList;

    }


    public static void main(String[] args) {
        System.out.println(getShardingTableName(new Date()));
        System.out.println(getAddMonth(new Date(),8));

        Date endDate = ShardingDateUtil.getAddMonth(new Date(),12);
        List<Date> dates = getBetweenMonths(new Date(),endDate);

        for(Date date : dates) {
            System.out.println("date="+dateToString(date));
        }


    }


}
