package com.kq.sharding.mapper;

import com.kq.sharding.util.ShardingDataSourceUtil;
import com.kq.sharding.util.ShardingDateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.shardingjdbc.jdbc.core.datasource.ShardingDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;



/**
 * @author kq
 * @date 2021-05-28 15:25
 * @since 2020-0630
 */
@Slf4j
@Service
public class TableMapper {

    @Autowired
    ShardingDataSource shardingDataSource;

//    public static final String SALE_ORDER_TABLE_PREFIX = "";

//    @Select(" show tables ")
    public List<String> getTables() {

        DataSource ds = ShardingDataSourceUtil.getDataSource(shardingDataSource);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);

        return jdbcTemplate.queryForList("show tables",String.class);

    }

    // 创建新表 t_order
    // CREATE TABLE $newTable LIKE $oldTable;

    public int createSaleOrderTable(List<Date> months) {

        if(CollectionUtils.isEmpty(months)) {
            return 0;
        }

        DataSource ds = ShardingDataSourceUtil.getDataSource(shardingDataSource);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
        String sql = "CREATE TABLE IF NOT EXISTS t_order_%s LIKE t_order_temp";

        int result = 0;
        for(Date month : months) {
            String newSql = String.format(sql, ShardingDateUtil.getShardingTableName(month));
            int tempResult = jdbcTemplate.update(newSql);
            result+=tempResult;
            log.info("创建订单表sql={} 执行结果={}",newSql,tempResult);
        }
        return result;

    }

    public int createSaleOrderDetailTable(List<Date> months) {

        if(CollectionUtils.isEmpty(months)) {
            return 0;
        }

        DataSource ds = ShardingDataSourceUtil.getDataSource(shardingDataSource);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
        String sql = "CREATE TABLE IF NOT EXISTS t_order_detail_%s LIKE t_order_detail_temp";

        int result = 0;
        for(Date month : months) {
            String newSql = String.format(sql, ShardingDateUtil.getShardingTableName(month));
            int tempResult = jdbcTemplate.update(newSql);
            result+=tempResult;
            log.info("创建订单明细表sql={} 执行结果={}",newSql,tempResult);
        }
        return result;

    }

//    public static void main(String[] args) {
//        String sql = "CREATE TABLE t_order_%s LIKE t_order_temp";
//
//        System.out.println(String.format(sql, "202105"));
//
//    }

}
