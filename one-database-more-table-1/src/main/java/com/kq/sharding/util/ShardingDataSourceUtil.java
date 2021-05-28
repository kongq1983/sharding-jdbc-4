package com.kq.sharding.util;

import org.apache.shardingsphere.shardingjdbc.jdbc.core.datasource.ShardingDataSource;

import javax.sql.DataSource;

/**
 * @author kq
 * @date 2021-05-28 16:52
 * @since 2020-0630
 */
public class ShardingDataSourceUtil {

    public static final String DEFAULT_DS_NAME = "ds1";

    public static DataSource getDataSource(ShardingDataSource shardingDataSource){
        return shardingDataSource.getDataSourceMap().get(DEFAULT_DS_NAME);
    }

}
