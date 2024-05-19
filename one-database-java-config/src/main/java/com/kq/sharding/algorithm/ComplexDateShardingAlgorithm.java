package com.kq.sharding.algorithm;

import com.google.common.collect.Range;
import com.kq.sharding.util.ShardingDateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;

import java.util.*;

/**
 * ComplexShardingAlgorithm
 *
 * @author kq
 * @date 2024-05-19 21:32
 * @since 1.0.0
 */
@Slf4j
public class ComplexDateShardingAlgorithm implements ComplexKeysShardingAlgorithm<Date> {


    @Override
    public Collection<String> doSharding(Collection<String> collection, ComplexKeysShardingValue<Date> shardingValue) {

        String logicTableName = shardingValue.getLogicTableName().toLowerCase();
        // 值分片  键是列名（字段名），值是一个集合，集合中包含了该列的所有分片键值
        Map<String, Collection<Date>> equalMap = shardingValue.getColumnNameAndShardingValuesMap();
        // 范围分片  值是一个Range对象的集合，每个Range对象定义了一个范围（区间）
        Map<String, Range<Date>> rangeMap = shardingValue.getColumnNameAndRangeValuesMap();

        log.info("collection={}",collection);
        log.info("logicTableName={}",logicTableName);
        log.info("equalMap={}",equalMap);
        log.info("rangeMap={}",rangeMap);

        Set<String> tableNames = new TreeSet<>();

        if(isNotEmpty(equalMap)) {

            for (Map.Entry<String, Collection<Date>> kv :  equalMap.entrySet()) {

                for(Date date : kv.getValue()) {

                    tableNames.add(getTableName(logicTableName,date));

                    return tableNames;
                }

            }


        }else if (isNotEmpty(rangeMap)) {

            for (Map.Entry<String, Range<Date>> kv : rangeMap.entrySet()) {

                Range<Date> range = kv.getValue();
                Date lower = range.lowerEndpoint();
                Date upper = range.upperEndpoint();

                if(lower!=null && upper!=null){
                    List<Date> dates =  ShardingDateUtil.getBetweenDates(lower,upper);
                    for(Date date : dates) {
                        tableNames.add(getTableName(logicTableName,date));
                    }
                } else {

                    if(lower!=null){
                        tableNames.add(getTableName(logicTableName,lower));
                    }

                    if(upper!=null){
                        tableNames.add(getTableName(logicTableName,upper));
                    }
                }

            }

        }



        return tableNames;
    }


    private String getTableName(String logicTableName,Date date) {
        return logicTableName+"_"+ShardingDateUtil.getRealTableName(date);
    }

    public static final boolean isNotEmpty(Map map) {
        return null != map && !map.isEmpty();
    }

}
