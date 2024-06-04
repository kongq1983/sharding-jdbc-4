package com.kq.sharding.algorithm;

import com.kq.sharding.util.ShardingDateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.util.*;

/**
 * @author kq
 * @date 2021-05-26 18:44
 * @since 2020-0630
 */
@Slf4j
public class OrderTimeRangeShardingAlgorithm implements RangeShardingAlgorithm<Date> {

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<Date> shardingValue) {

        String logicTableName = shardingValue.getLogicTableName();

        // 只有开始日期或结束日期，默认是该月
        Date lower = null;
        try {
            lower = shardingValue.getValueRange().lowerEndpoint();
        }catch (Exception e){
            // ingore
        }

        Date upper = null;
        try {
            upper = shardingValue.getValueRange().upperEndpoint();
        }catch (Exception e){
            // ignore
        }

        Set<String> tablenames = new TreeSet();

        if(lower!=null && upper!=null){
            List<Date> dates =  ShardingDateUtil.getBetweenDates(lower,upper);
            for(Date date : dates) {
                tablenames.add(getTableName(logicTableName,date));
            }
        } else {

            if(lower!=null){
                tablenames.add(getTableName(logicTableName,lower));
            }

            if(upper!=null){
                tablenames.add(getTableName(logicTableName,upper));
            }
        }

        log.info("order range lower={} upper={}",lower,upper);


        return tablenames;
    }

    private String getTableName(String logicTableName,Date date) {
        return logicTableName+"_"+ShardingDateUtil.getShardingTableName(date);
    }

}
