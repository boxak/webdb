package com.example.webdb.test;

import com.example.webdb.dao.WebdbDao;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/test-applicationContext.xml")
public class WebdbJdbcDaoTests {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    WebdbDao dao;

    @Test
    public void getColumnNames() {

        String tableName = "users";
        String dbName = "study_db";

        String query = "SELECT COLUMN_NAME\n" +
                "FROM INFORMATION_SCHEMA.COLUMNS\n" +
                "WHERE TABLE_SCHEMA='" + dbName +"'\n" +
                "AND TABLE_NAME='"+tableName+"'";
        List<String> columnNames = this.jdbcTemplate.query(query,
                new RowMapper<String>() {
                    @Override
                    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return rs.getString(1);
                    }
                }
        );

        Assertions.assertThat(columnNames).isNotNull();

        for (String column : columnNames) {
            log.info("column : " + column);
        }

    }

    @Test
    public void select() {
        String query = "select * from study_db.users";
        String dbName = "study_db";
        String tableName = "users";

        List<JSONObject> list = dao.select(query, dbName, tableName);

        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSizeGreaterThan(0);

        for (JSONObject obj : list) {
            log.info("obj is : " + obj);
        }

    }

}
