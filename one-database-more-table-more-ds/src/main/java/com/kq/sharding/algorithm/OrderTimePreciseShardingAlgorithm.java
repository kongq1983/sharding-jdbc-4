package com.kq.sharding.algorithm;

import com.kq.sharding.util.ShardingDateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;
import java.util.Date;

/**
 * @author kq
 * @date 2021-05-25 16:05
 * @since 2020-0630
 */
@Slf4j
public class OrderTimePreciseShardingAlgorithm implements PreciseShardingAlgorithm<Date> {


    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Date> preciseShardingValue) {

        String logicTableName = preciseShardingValue.getLogicTableName();
        Date value = preciseShardingValue.getValue();
        String column = preciseShardingValue.getColumnName();

        log.info("logicTableName={} column={} value={}",logicTableName,column,value);


        StringBuffer tableName = new StringBuffer();
        tableName.append(preciseShardingValue.getLogicTableName())
                .append("_").append(ShardingDateUtil.getShardingTableName(preciseShardingValue.getValue()));

        return tableName.toString();

    }
}
