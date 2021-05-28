package com.kq.sharding.task;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.core.rule.ShardingRule;
import org.apache.shardingsphere.core.rule.TableRule;
import org.apache.shardingsphere.shardingjdbc.jdbc.core.datasource.ShardingDataSource;
import org.apache.shardingsphere.underlying.common.rule.DataNode;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.List;

/**
 * @author kq
 * @date 2021-05-28 16:50
 * @since 2020-0630
 */

@Component
@EnableScheduling
@Slf4j
@Order(9999)  //需要依赖字典配置，必须在字典启动后启动
public class ShardingTableRuleActualTablesRefreshSchedule  implements InitializingBean {

    @Autowired
    ShardingDataSource shardingDataSource;


    @Scheduled(cron = "0 */1 * * * ?")
    public void actualTablesRefresh() throws NoSuchFieldException, IllegalAccessException {
        log.info("-----------------start----------------");
        //版本4.0.0-RC1 以上版本支持写法
//        ShardingDataSource shardingDataSource = (ShardingDataSource)shardingDataSource;
        ShardingRule shardingRule = shardingDataSource.getRuntimeContext().getRule();

        //版本4.0.0-RC1,包含本版本，以及以下版本
//        ShardingRule shardingRule = ApplicationContextHolder.getBean(ShardingDataSource.class).getShardingContext().getShardingRule();
//        List<String> names = dynamicTables.getNames();

        Collection<TableRule> tableRules = shardingRule.getTableRules();

        for (TableRule tableRule :  tableRules) {
            List<DataNode> dataNodes = tableRule.getActualDataNodes();

            Field actualDataNodesField = TableRule.class.getDeclaredField("actualDataNodes");
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(actualDataNodesField, actualDataNodesField.getModifiers() & ~Modifier.FINAL);

            for(DataNode ds : dataNodes) {
                log.info("=========== logicTable={} tableName={}",tableRule.getLogicTable(),ds.getTableName());
            }



            /**
            //key xxxx.dynamic.table.date  如xxxx202005开始 value = 202005
            //如下会从202005 计算到当前月
            CacheDictVo dictVo = DictTools.findByCode(tableRule.getLogicTable() + DYNAMIC_TABLES);
            String dictValue;
            if (dictVo != null){
                dictValue = dictVo.getValue();
            }else {
                log.error("init test 202005  is  bug");
                dictValue = "202005";
            }


            Date parse = DateTimeUtil.parse(dictValue);

            LocalDateTime localDateTime = DateTimeUtil.date2LocalDateTime(parse);
            LocalDateTime now = LocalDateTime.now();

            String dataSourceName = dataNodes.get(0).getDataSourceName();
            String logicTableName = tableRule.getLogicTable();
            StringBuilder stringBuilder = new StringBuilder()
                    .append(dataSourceName).append(".").append(logicTableName);
            final int length = stringBuilder.length();
            List<DataNode> newDataNodes = new ArrayList<>();
            while (true) {
                stringBuilder.setLength(length);
                stringBuilder.append(localDateTime.format(DateTimeFormatter.ofPattern("yyyyMM")));
                DataNode dataNode = new DataNode(stringBuilder.toString());
                newDataNodes.add(dataNode);
                localDateTime = localDateTime.plusMonths(1L);
                if (localDateTime.isAfter(now)) {
                    break;
                }
            }
            actualDataNodesField.setAccessible(true);
            actualDataNodesField.set(tableRule, newDataNodes);

            Set<String> actualTables = Sets.newHashSet();
            Map<DataNode, Integer> dataNodeIntegerMap = Maps.newHashMap();

            AtomicInteger a = new AtomicInteger(0);
            newDataNodes.forEach((dataNode -> {
                actualTables.add(dataNode.getTableName());
                if (a.intValue() == 0){
                    a.incrementAndGet();
                    dataNodeIntegerMap.put(dataNode, 0);
                }else {
                    dataNodeIntegerMap.put(dataNode, a.intValue());
                    a.incrementAndGet();
                }
            }));

            //动态刷新：actualTables
            Field actualTablesField = TableRule.class.getDeclaredField("actualTables");
            actualTablesField.setAccessible(true);
            actualTablesField.set(tableRule, actualTables);
            //动态刷新：dataNodeIndexMap
            Field dataNodeIndexMapField = TableRule.class.getDeclaredField("dataNodeIndexMap");
            dataNodeIndexMapField.setAccessible(true);
            dataNodeIndexMapField.set(tableRule, dataNodeIntegerMap);
            //动态刷新：datasourceToTablesMap
            Map<String, Collection<String>> datasourceToTablesMap = Maps.newHashMap();
            datasourceToTablesMap.put(dataSourceName, actualTables);
            Field datasourceToTablesMapField = TableRule.class.getDeclaredField("datasourceToTablesMap");
            datasourceToTablesMapField.setAccessible(true);
            datasourceToTablesMapField.set(tableRule, datasourceToTablesMap);
             **/
            log.info("-----------------end----------------");
        }

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        actualTablesRefresh();
    }

}
