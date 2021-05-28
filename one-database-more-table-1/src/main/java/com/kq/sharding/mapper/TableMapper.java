package com.kq.sharding.mapper;

import com.kq.sharding.util.ShardingDataSourceUtil;
import org.apache.shardingsphere.shardingjdbc.jdbc.core.datasource.ShardingDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;



/**
 * @author kq
 * @date 2021-05-28 15:25
 * @since 2020-0630
 */
@Service
public class TableMapper {

    @Autowired
    ShardingDataSource shardingDataSource;

//    @Select(" show tables ")
    public List<String> getTables() {

        DataSource ds = ShardingDataSourceUtil.getDataSource(shardingDataSource);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);

        return jdbcTemplate.queryForList("show tables",String.class);

    }

}
