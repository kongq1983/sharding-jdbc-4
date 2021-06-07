package com.kq.sharding.dao;

import com.kq.sharding.entity.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Map;

/**
 * @author kq
 * @date 2021-06-07 18:07
 * @since 2020-0630
 */
@Slf4j
@Service
public class AccountDao {

    public void addAccount(JdbcTemplate jdbcTemplate,Account o) {

        String sql = "insert into t_account(username,phone,createTime) values(?,?,?)";

        jdbcTemplate.update(sql,o.getUsername(),o.getPhone(),new Date(System.currentTimeMillis()));

    }

    public void list(JdbcTemplate jdbcTemplate, int type) {
        String sql = "select * from t_account";

        List<Map<String, Object>> list  = jdbcTemplate.queryForList(sql);

        log.info("type={} list={}",type,list);

    }

}
