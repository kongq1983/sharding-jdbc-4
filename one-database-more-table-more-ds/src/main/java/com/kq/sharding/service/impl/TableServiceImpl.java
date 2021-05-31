package com.kq.sharding.service.impl;

import com.kq.sharding.mapper.TableMapper;
import com.kq.sharding.service.TableService;
import com.kq.sharding.util.ShardingDateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author kq
 * @date 2021-05-29 13:55
 * @since 2020-0630
 */
@Slf4j
@Service
public class TableServiceImpl implements TableService {

    @Autowired
    private TableMapper tableMapper;

    @Override
    public int createSaleOrderLogic() {
        // 今天
        Date now = new Date();
        // 今后12个月
        Date endDate = ShardingDateUtil.getAddMonth(now,12);

        log.debug("创建表结构结束时间 = {}",ShardingDateUtil.dateToString(endDate));

        List<Date> months =  ShardingDateUtil.getBetweenMonths(now,endDate);

        // 创建订单表
        int reuslt = tableMapper.createSaleOrderTable(months);
        // 创建订单明细表
        int detailResult = tableMapper.createSaleOrderDetailTable(months);

        return Math.max(reuslt,detailResult);


    }

}
