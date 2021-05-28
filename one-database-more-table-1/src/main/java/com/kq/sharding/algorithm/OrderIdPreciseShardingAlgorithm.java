package com.kq.sharding.algorithm;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * 订单ID
 * @author kq
 * @date 2021-05-25 16:05
 * @since 2020-0630
 */
@Slf4j
public class OrderIdPreciseShardingAlgorithm implements PreciseShardingAlgorithm<String> {


    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<String> preciseShardingValue) {

        StringBuffer tableName = new StringBuffer();
        String logicTableName = preciseShardingValue.getLogicTableName();
        String dealLogicTableName = logicTableName.replace("_a","");
        String value = preciseShardingValue.getValue();
        String column = preciseShardingValue.getColumnName();

        log.info("=================id logicTableName={} dealLogicTableName={} column={} value={}",logicTableName,dealLogicTableName,column,value);

        tableName.append(dealLogicTableName).append("_").append(value, 0, 6);

        return tableName.toString();

    }
}
