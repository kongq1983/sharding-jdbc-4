package com.kq.sharding.jdbc.api;

import org.apache.shardingsphere.api.config.sharding.KeyGeneratorConfiguration;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.StandardShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 表达式: https://shardingsphere.apache.org/document/legacy/4.x/document/cn/features/sharding/other-features/inline-expression/
 * 数据库有2个 test0(account_0、account_2) teset1(account_1、account_3)
 *
 */
public class APIConfiguration {

    DataSource getShardingDataSource() throws SQLException {
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        shardingRuleConfig.getTableRuleConfigs().add(getOrderTableRuleConfiguration());
//        shardingRuleConfig.getTableRuleConfigs().add(getOrderItemTableRuleConfiguration());
//        shardingRuleConfig.getBindingTableGroups().add("t_order, t_order_item");
        shardingRuleConfig.getBindingTableGroups().add("account");
        shardingRuleConfig.getBroadcastTables().add("dict"); // 广播 每个表都会插入
//        shardingRuleConfig.setDefaultDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("id", "${id % 2}"));
//        shardingRuleConfig.setDefaultTableShardingStrategyConfig(new InlineShardingStrategyConfiguration("id", "${id % 2}"));
        return ShardingDataSourceFactory.createDataSource(createDataSourceMap(), shardingRuleConfig, new Properties());
    }

    private KeyGeneratorConfiguration getKeyGeneratorConfiguration() {
        KeyGeneratorConfiguration result = new KeyGeneratorConfiguration("SNOWFLAKE", "id");
        return result;
    }

    TableRuleConfiguration getOrderTableRuleConfiguration() {
//        TableRuleConfiguration result = new TableRuleConfiguration("account", "test$->{1..2}.account");
        TableRuleConfiguration result = new TableRuleConfiguration("account", "test0.account_0,test0.account_2,test1.account_1,test1.account_3");
        result.setKeyGeneratorConfig(getKeyGeneratorConfiguration());
//        result.setDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("id", "test$->{(id % 2)+1}"));
        // id=1:在ds1   id=2:在ds0  单数在1库，偶数在0库
        result.setDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("id", "test$->{(id % 2)}"));
        //固定account表 account_1、account_3在ds1   account_0、account_2在ds2
        result.setTableShardingStrategyConfig(new InlineShardingStrategyConfiguration("id", "account_$->{(id % 4)}"));
        // 动态选择表
//        result.setTableShardingStrategyConfig(new InlineShardingStrategyConfiguration("id", "t_order${order_id % 2}"));

//        result.setTableShardingStrategyConfig();
        return result;
    }

//    TableRuleConfiguration getOrderItemTableRuleConfiguration() {
//        TableRuleConfiguration result = new TableRuleConfiguration("t_order_item", "ds${0..1}.t_order_item${0..1}");
//        return result;
//    }

    Map<String, DataSource> createDataSourceMap() {
        Map<String, DataSource> result = new HashMap<>();
//        result.put("test1", createDataSource("test1"));
//        result.put("test2", createDataSource("test2"));
        result.put("test0", createDataSource("test0"));
        result.put("test1", createDataSource("test1"));
        return result;
    }

    private DataSource createDataSource(String database){

        com.zaxxer.hikari.HikariDataSource dataSource = new com.zaxxer.hikari.HikariDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/"+database+"?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");

        return dataSource;
    }
}
