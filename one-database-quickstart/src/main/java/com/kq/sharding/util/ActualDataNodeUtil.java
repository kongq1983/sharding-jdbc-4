package com.kq.sharding.util;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author: kq
 * @date: 2024-04-30 16:52:25
 * @since: 1.0.0
 * @description:
 */
public class ActualDataNodeUtil {


    /**
     * 获取实际表
     * @return
     */
    public static List<String> getActualDataNodes() {
//        return Lists.newArrayList("t_order_202404","t_order_202405","t_order_202406");
        return Lists.newArrayList("202404","202405","202406");
    }


}
