package com.kq.sharding.task;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.kq.sharding.mapper.TableMapper;
import com.kq.sharding.service.TableService;
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
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

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

    @Autowired
    private TableService tableService;

    @Autowired
    private TableMapper tableMapper;

    private static Map<String,String> LOGIC_TABLE_MAP = new ConcurrentHashMap<>();

    public static final String VARA = "_a";
    public static final String ORDER_TABLE = "t_order";
    public static final String ORDER_TABLE_DETAIL = "t_order_detail";

    static {
        LOGIC_TABLE_MAP.put(ORDER_TABLE,ORDER_TABLE);
        LOGIC_TABLE_MAP.put(ORDER_TABLE+VARA,ORDER_TABLE);
        LOGIC_TABLE_MAP.put(ORDER_TABLE_DETAIL,ORDER_TABLE_DETAIL);
        LOGIC_TABLE_MAP.put(ORDER_TABLE_DETAIL+VARA,ORDER_TABLE_DETAIL);
    }


    @Scheduled(cron = "0 */1 * * * ?")
    public void actualTablesRefresh() throws NoSuchFieldException, IllegalAccessException {
        actualTablesRefreshCommon(true);
    }


    public void actualTablesRefreshCommon(boolean scheduled) throws NoSuchFieldException, IllegalAccessException {

        int result = tableService.createSaleOrderLogic();
        log.debug("自动创建订单-订单明细表结构 scheduled={} 返回结果={}",scheduled,result);

        if(scheduled==true && result==0) {
            return;
        }

        // 当前库中的所有表
        List<String> tables = tableMapper.getTables();

        if(CollectionUtils.isEmpty(tables)){
            log.warn("未成功获取当前库中的所有表，tables为空!");
            return;
        }


        log.info("-----------------start----------------");
        //版本4.0.0-RC1 以上版本支持写法
//        ShardingDataSource shardingDataSource = (ShardingDataSource)shardingDataSource;
        ShardingRule shardingRule = shardingDataSource.getRuntimeContext().getRule();




        //版本4.0.0-RC1,包含本版本，以及以下版本
//        ShardingRule shardingRule = ApplicationContextHolder.getBean(ShardingDataSource.class).getShardingContext().getShardingRule();
//        List<String> names = dynamicTables.getNames();

        Collection<TableRule> tableRules = shardingRule.getTableRules();

        for (TableRule tableRule :  tableRules) {
            // private final List<DataNode> actualDataNodes;
            List<DataNode> dataNodes = tableRule.getActualDataNodes();

            Field actualDataNodesField = TableRule.class.getDeclaredField("actualDataNodes");
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(actualDataNodesField, actualDataNodesField.getModifiers() & ~Modifier.FINAL);

//            for(DataNode ds : dataNodes) {
////                logicTable=t_order_detail_a tableName=t_order_detail_202110
//                log.info("=========== logicTable={} tableName={}",tableRule.getLogicTable(),ds.getTableName());
//            }

            // logicTable=t_order t_order_a
            // logicTable=t_order_detail t_order_detail_a

            // 先创建表 修改actualDataNodes

            // 然后动态更新datasourceToTablesMap
            // Map<String, Collection<String>> datasourceToTablesMap

            String logicTable = tableRule.getLogicTable().toLowerCase();

            String realTablePrefix = LOGIC_TABLE_MAP.get(logicTable);
            if(realTablePrefix==null){
                log.warn("未配置逻辑表对应关系! logicTable={}",logicTable);
                return;
            }

            List<String> matchTables = tables.stream().filter(s-> s.matches(realTablePrefix+"_(\\d){6}")).sorted().collect(Collectors.toList());

            if(CollectionUtils.isEmpty(matchTables)){
                log.warn("未找到匹配的表！表前缀为realTablePrefix={} 全部表结构={}",realTablePrefix,tables);
                return;
            }


            // ds1.t_order_202105
            String dataSourceName = dataNodes.get(0).getDataSourceName();
            String logicTableName = tableRule.getLogicTable();
//            StringBuilder stringBuilder = new StringBuilder()
//                    .append(dataSourceName).append(".").append(logicTableName);
            List<DataNode> newDataNodes = new ArrayList<>();

            for(String table : matchTables) {
                DataNode dataNode = new DataNode(dataSourceName+"."+table);
                newDataNodes.add(dataNode);
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

//            Field datasourceToTablesMapField = TableRule.class.getDeclaredField("datasourceToTablesMap");
//            datasourceToTablesMapField.setAccessible(true);
//
//            Object datasourceToTablesMap = datasourceToTablesMapField.get(tableRule);
//            log.info("datasourceToTablesMap={}",datasourceToTablesMap);


            log.info("-----------------end----------------");
        }

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        actualTablesRefreshCommon(false);
    }

}
