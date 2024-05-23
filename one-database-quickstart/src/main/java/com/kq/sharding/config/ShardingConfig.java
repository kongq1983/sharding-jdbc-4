package com.kq.sharding.config;

/**
 * ShardingConfig
 *
 * @author kq
 * @date 2024-05-19 20:25
 * @since 1.0.0
 */

import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.log.StaticLog;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.core.rule.ShardingRule;
import org.apache.shardingsphere.core.rule.TableRule;
import org.apache.shardingsphere.shardingjdbc.jdbc.core.datasource.ShardingDataSource;
import org.apache.shardingsphere.underlying.common.rule.DataNode;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

//@ConditionalOnProperty(name = "db.type", havingValue = "mysql",
//        matchIfMissing = true)
@Slf4j
@Configuration
//@AutoConfigureAfter({ MysqlConfig.class})
public class ShardingConfig implements InitializingBean {

    @Resource
    DataSource dataSource;

//    @Resource
//    ShardingDataSource shardingDataSource;

    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }


    public void init() throws SQLException, NoSuchFieldException, IllegalAccessException, IOException {
        StaticLog.info("[ShardingConfig][init] running...");
        setTableSubInfo(dataSource);
    }

    private void setTableSubInfo(DataSource dataSource) throws SQLException, NoSuchFieldException, IllegalAccessException {
        ApplicationContext applicationContext = SpringUtil.getApplicationContext();
        ShardingDataSource shardingDataSource = (ShardingDataSource)applicationContext.getBean("shardingDataSource");
        ShardingRule shardingRule = shardingDataSource.getRuntimeContext().getRule();
//        String dbUrl = SpringUtil.getApplicationContext().getEnvironment().getProperty("spring.datasource.url");
//        String dbName = dbUrl.substring(dbUrl.lastIndexOf("/") + 1, dbUrl.indexOf("?"));
        String dbName = "attendancedb";
        String sqlStr = "select table_name from information_schema.tables where table_schema = '" + dbName + "' and table_name REGEXP '#{tableName}_[0-9]{6}' order by table_name";
        TableRule tr;
        ResultSet res;
        String dataSourceName = "dataSource_for_sharding";
        StringBuilder tableNode = new StringBuilder();
        List<DataNode> nodes = new ArrayList<>(20);
        Statement se = dataSource.getConnection().createStatement();

        String[] shardingTables = {"ad_attendance_result", "ad_in_out_record"};

        // 按月分表的表规则放入
        for (String subTableName : shardingTables) {
            tr = shardingRule.getTableRule(subTableName);
            String sql = sqlStr.replace("#{tableName}", subTableName);
            res = se.executeQuery(sql);
            nodes = new ArrayList<>();
            while (res.next()) {
                tableNode = new StringBuilder();
                String tableName = res.getString("table_name");
                log.debug("分表-真实表 actualTableName: {}",tableName);
                nodes.add(new DataNode(tableNode.append(dataSourceName).append(".").append(tableName).toString()));
            }
            refreshTableRule(nodes, tr, dataSourceName);
        }

    }

    private void refreshTableRule(List<DataNode> nodes, TableRule tr, String dataSourceName) throws NoSuchFieldException, IllegalAccessException{
        Set<String> actualTables = new TreeSet<>();
        Map<DataNode, Integer> dataNodeIndexMap = new HashMap<>();
        AtomicInteger index = new AtomicInteger(0);
        nodes.forEach(node -> {
            dataNodeIndexMap.put(node, index.intValue());
            actualTables.add(node.getTableName());
            index.set(index.intValue() + 1);
        });
        // 动态刷新：actualDataNodesField
        Field actualDataNodesField = TableRule.class.getDeclaredField("actualDataNodes");
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(actualDataNodesField, actualDataNodesField.getModifiers() & ~Modifier.FINAL);
        actualDataNodesField.setAccessible(true);
        actualDataNodesField.set(tr, nodes);
        // 动态刷新：actualTablesField
        Field actualTablesField = TableRule.class.getDeclaredField("actualTables");
        actualTablesField.setAccessible(true);
        actualTablesField.set(tr, actualTables);
        // 动态刷新：dataNodeIndexMapField
        Field dataNodeIndexMapField = TableRule.class.getDeclaredField("dataNodeIndexMap");
        dataNodeIndexMapField.setAccessible(true);
        dataNodeIndexMapField.set(tr, dataNodeIndexMap);
        // 动态刷新：datasourceToTablesMapField
        Map<String, Collection<String>> datasourceToTablesMap = new HashMap<>();
        datasourceToTablesMap.put(dataSourceName, actualTables);
        Field datasourceToTablesMapField = TableRule.class.getDeclaredField("datasourceToTablesMap");
        datasourceToTablesMapField.setAccessible(true);
        datasourceToTablesMapField.set(tr, datasourceToTablesMap);
    }

}

