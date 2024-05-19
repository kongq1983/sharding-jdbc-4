package com.kq.sharding.config;

import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.log.StaticLog;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.kq.sharding.algorithm.ComplexDateShardingAlgorithm;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.ComplexShardingStrategyConfiguration;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.apache.shardingsphere.shardingjdbc.jdbc.core.datasource.ShardingDataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;

//@ConditionalOnProperty(name = "db.type", havingValue = "mysql",
//        matchIfMissing = true)
@Configuration
public class MysqlConfig {

//    @Bean(name = "dataSource")
//    DataSource dataSource(DataSourceProperties zyRoxDsProperties){
//        StaticLog.info("[DataSourceConfig][mysql][shardingDataSource] running... db.type is {}", SpringUtil.getProperty("db.type"));
//        return DataSourceConfig.reflectDataSource(zyRoxDsProperties);
//    }

    @Bean(name = "dataSource")
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public HikariDataSource dataSource(DataSourceProperties properties) {
        HikariDataSource dataSource = createDataSource(properties, HikariDataSource.class);
        if (StringUtils.hasText(properties.getName())) {
            dataSource.setPoolName(properties.getName());
        }
        return dataSource;
    }

    protected static <T> T createDataSource(DataSourceProperties properties, Class<? extends DataSource> type) {
        return (T) properties.initializeDataSourceBuilder().type(type).build();
    }

    @Bean(name = "shardingDataSource")
    ShardingDataSource shardingDataSource(DataSource dataSource) throws SQLException {
        StaticLog.info("[DataSourceConfig][mysql][dataSource] running... db.type is {}", SpringUtil.getProperty("db.type"));
        Map<String, DataSource> forSharding = Maps.newHashMap();
        forSharding.put("dataSource_for_sharding", dataSource);
        ShardingRuleConfiguration shardingRuleConfiguration = new ShardingRuleConfiguration();
        ArrayList<TableRuleConfiguration> tableRuleConfigurations = Lists.newArrayList(
                ruleConfiguration("ad_attendance_result","dataSource_for_sharding.ad_attendance_result","attendance_date",new ComplexDateShardingAlgorithm()),
                ruleConfiguration("ad_in_out_record","dataSource_for_sharding.ad_in_out_record","in_out_time",new ComplexDateShardingAlgorithm())
        );
        shardingRuleConfiguration.setTableRuleConfigs(tableRuleConfigurations);
        shardingRuleConfiguration.setBindingTableGroups(
                Lists.newArrayList("ad_attendance_result","ad_in_out_record")
        );
        Properties properties = new Properties();
        properties.setProperty("sql.show","true");
        properties.setProperty("max.connections.size.per.query","1");
        properties.setProperty("allow.range.query.with.inline.sharding","1");
        return (ShardingDataSource) ShardingDataSourceFactory.createDataSource(forSharding, shardingRuleConfiguration, properties);
    }


    private TableRuleConfiguration ruleConfiguration(String logicTable, String acNodes, String columns, ComplexKeysShardingAlgorithm<?> shardingAlgorithm ){
        TableRuleConfiguration configuration = new TableRuleConfiguration(logicTable, acNodes);
        configuration.setTableShardingStrategyConfig(strategyConfiguration(columns,shardingAlgorithm));
        return configuration;
    }

    private ComplexShardingStrategyConfiguration strategyConfiguration(String columns, ComplexKeysShardingAlgorithm<?> complexKeysShardingAlgorithm){
        return new ComplexShardingStrategyConfiguration(columns,complexKeysShardingAlgorithm);
    }
}
