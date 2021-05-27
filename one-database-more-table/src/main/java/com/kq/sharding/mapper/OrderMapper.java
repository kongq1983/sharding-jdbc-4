package com.kq.sharding.mapper;

import com.github.pagehelper.Page;
import com.kq.sharding.entity.Order;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * AccountMapper
 *
 * @author1 kq
 * @date 2019-10-12
 */
@Mapper
public interface OrderMapper {

    @Select("select id,user_id userId,code,sale_date saleDate,create_time createTime from t_order limit 20")
    public List<Order> getOrderList();

    /**
     * STR_TO_DATE不会分片
     * @return
     */
    @Select("select id,user_id userId,code,sale_date saleDate,create_time createTime from t_order where sale_date> STR_TO_DATE('2021-07-01 10:20:30','%Y-%m-%d') and sale_date< STR_TO_DATE('2021-09-01 10:20:30','%Y-%m-%d')")
    public List<Order> getOrderListBySaleDate();

    // 查时间范围内的表
    /**
     * 会分片 - OrderTimeRangeShardingAlgorithm
     * @param startDate
     * @param endDate
     * @return
     */
    @Select("select id,user_id userId,code,sale_date saleDate,create_time createTime from t_order where sale_date> #{startDate} and sale_date< #{endDate}")
    public List<Order> getOrderListBySaleDate1(@Param("startDate") Date startDate, @Param("endDate")Date endDate);

    // 查时间范围内的表
    /**
     * 会分片 - OrderTimeRangeShardingAlgorithm
     * @param startDate
     * @param endDate
     * @return
     */
    @Select("select id,user_id userId,code,sale_date saleDate,create_time createTime from t_order where sale_date between #{startDate} and #{endDate}")
    public List<Order> getOrderListBySaleDateBetween(@Param("startDate") Date startDate, @Param("endDate")Date endDate);


    @Insert("insert into t_order(id,user_id,code,sale_date,create_time) values(#{id},#{userId},#{code},#{saleDate},#{createTime})")
    void addOrder(Order o);

//    @Insert("insert into t_order(username,phone,createTime) values(#{username},#{phone},#{createTime})")
//    void addAccountNoId(Order o);


    //t_order 根据id匹配只查现在有多少个订单表，非笛卡尔积
//    2021-05-27 15:49:49.083 [http-nio-10000-exec-1] INFO  ShardingSphere-SQL-Actual SQL: ds1 ::: select id,user_id userId,code,sale_date saleDate,create_time createTime from t_order_202105 where id=? ::: [202107123]
//    2021-05-27 15:49:49.083 [http-nio-10000-exec-1] INFO  ShardingSphere-SQL-Actual SQL: ds1 ::: select id,user_id userId,code,sale_date saleDate,create_time createTime from t_order_202106 where id=? ::: [202107123]
//    2021-05-27 15:49:49.083 [http-nio-10000-exec-1] INFO  ShardingSphere-SQL-Actual SQL: ds1 ::: select id,user_id userId,code,sale_date saleDate,create_time createTime from t_order_202107 where id=? ::: [202107123]
//    2021-05-27 15:49:49.083 [http-nio-10000-exec-1] INFO  ShardingSphere-SQL-Actual SQL: ds1 ::: select id,user_id userId,code,sale_date saleDate,create_time createTime from t_order_202108 where id=? ::: [202107123]
//    2021-05-27 15:49:49.083 [http-nio-10000-exec-1] INFO  ShardingSphere-SQL-Actual SQL: ds1 ::: select id,user_id userId,code,sale_date saleDate,create_time createTime from t_order_202109 where id=? ::: [202107123]
//    2021-05-27 15:49:49.083 [http-nio-10000-exec-1] INFO  ShardingSphere-SQL-Actual SQL: ds1 ::: select id,user_id userId,code,sale_date saleDate,create_time createTime from t_order_202110 where id=? ::: [202107123]
    @Select("select id,user_id userId,code,sale_date saleDate,create_time createTime from t_order where id=#{id}")
    public Order getOrder(String id);


    //t_order_a 根据id匹配只查1个表
//    2021-05-27 15:50:56.642 [http-nio-10000-exec-4] INFO  c.k.s.algorithm.OrderIdPreciseShardingAlgorithm-=================id logicTableName=t_order_a dealLogicTableName=t_order column=id value=202107123
//    2021-05-27 15:50:56.642 [http-nio-10000-exec-4] INFO  ShardingSphere-SQL-Logic SQL: select id,user_id userId,code,sale_date saleDate,create_time createTime from t_order_a where id=?
//    2021-05-27 15:50:56.643 [http-nio-10000-exec-4] INFO  ShardingSphere-SQL-SQLStatement: SelectStatementContext(super=CommonSQLStatementContext(sqlStatement=org.apache.shardingsphere.sql.parser.sql.statement.dml.SelectStatement@5d75290d, tablesContext=org.apache.shardingsphere.sql.parser.binder.segment.table.TablesContext@64fbac81), tablesContext=org.apache.shardingsphere.sql.parser.binder.segment.table.TablesContext@64fbac81, projectionsContext=ProjectionsContext(startIndex=7, stopIndex=70, distinctRow=false, projections=[ColumnProjection(owner=null, name=id, alias=Optional.empty), ColumnProjection(owner=null, name=user_id, alias=Optional[userId]), ColumnProjection(owner=null, name=code, alias=Optional.empty), ColumnProjection(owner=null, name=sale_date, alias=Optional[saleDate]), ColumnProjection(owner=null, name=create_time, alias=Optional[createTime])]), groupByContext=org.apache.shardingsphere.sql.parser.binder.segment.select.groupby.GroupByContext@50484597, orderByContext=org.apache.shardingsphere.sql.parser.binder.segment.select.orderby.OrderByContext@703e471d, paginationContext=org.apache.shardingsphere.sql.parser.binder.segment.select.pagination.PaginationContext@41893f56, containsSubquery=false)
//    2021-05-27 15:50:56.644 [http-nio-10000-exec-4] INFO  ShardingSphere-SQL-Actual SQL: ds1 ::: select id,user_id userId,code,sale_date saleDate,create_time createTime from t_order_202107 where id=? ::: [202107123]
    @Select("select id,user_id userId,code,sale_date saleDate,create_time createTime from t_order_a where id=#{id}")
    public Order getOrderById(String id);

    @Delete("delete from t_order")
    public void deleteAll();

    @Select("SELECT i.* FROM t_order o JOIN t_order_detail i ON o.id=i.order_id WHERE o.id in ('202105258');")
    public List<Map<String,Object>> getOrderAndDetailList1();

    @Select("SELECT i.* FROM t_order o , t_order_detail i where o.id=i.order_id and o.id in ('202105258');")
    public List<Map<String,Object>> getOrderAndDetailList2();

    @Select("SELECT i.* FROM t_order o , t_order_detail i where i.order_id=o.id and o.id in ('202105258');")
    public List<Map<String,Object>> getOrderAndDetailList3();

    @Select("SELECT i.* FROM t_order_detail_a i,t_order_a o where i.order_id=o.id and o.id in ('202105258');")
    public List<Map<String,Object>> getOrderAndDetailList4();



//    @Select("SELECT i.* FROM t_order_detail i,t_order o where i.order_id=o.id and o.sale_date> #{startDate} and sale_date< #{endDate}")
//    @Select("SELECT i.* FROM t_order_detail i,t_order o where i.order_id=o.id and o.sale_date> #{startDate} and sale_date< #{endDate}")
//    @Select("SELECT i.* FROM t_order_detail i,t_order o where i.order_id=o.id and o.sale_date> #{startDate} and sale_date< #{endDate} and (o.id like '202107%' or o.id like '202108%')")
//    @Select("SELECT i.* FROM t_order_detail_a i,t_order_a o where i.order_id=o.id and o.sale_date> #{startDate} and o.sale_date< #{endDate}")
//    @Select("SELECT i.* FROM t_order_detail_a i,t_order_a o where i.order_id=o.id and i.sale_date=o.sale_date and o.sale_date> #{startDate} and o.sale_date< #{endDate}")
//    @Select("SELECT i.* FROM t_order o,t_order_detail i where i.sale_date=o.sale_date and i.order_id=o.id and o.sale_date> #{startDate} and o.sale_date< #{endDate}")
    // 会分片
    @Select("SELECT i.* FROM t_order o,t_order_detail i where i.order_id=o.id and o.sale_date> #{startDate} and o.sale_date< #{endDate}")
    public Page<Map<String,Object>> getOrderAndDetailListForPage(@Param("startDate") Date startDate, @Param("endDate")Date endDate);

//    @Select("SELECT i.* FROM t_order_detail_a i,t_order_a o where i.order_id=o.id and o.id in ('202107123','2021071239')")
    // 会走分片
//    @Select("SELECT i.* FROM t_order_detail_a i where i.order_id in ('202107123','2021071239')")
    // 会走分片
//    @Select("SELECT * FROM t_order_a o where o.id in ('202107123','2021071239')")
    // 会走分片
//    @Select("SELECT i.* FROM t_order_a o ,t_order_detail_a i where o.id=i.order_id and o.id in ('202107123','2021071239')")
    // 会走分片
//    @Select("SELECT i.* FROM t_order_a o ,t_order_detail_a i where i.order_id and o.id and o.id in ('202107123','2021071239')")
    // 不会走分片
//    @Select("SELECT i.* FROM t_order_a o ,t_order_detail_a i where i.order_id and o.id and i.order_id in ('202107123','2021071239')")
    // 会走分片
    @Select("SELECT i.* FROM t_order_detail_a i,t_order_a o where i.order_id and o.id and i.order_id in ('202107123','2021071239')")
    public Page<Map<String,Object>> getOrderAndDetailListForPage1(@Param("startDate") Date startDate, @Param("endDate")Date endDate);
    // 结论: 2张表关联（比如主表和明细表）只有是左边的表，条件是左边分片键的条件，才会走分片

}
