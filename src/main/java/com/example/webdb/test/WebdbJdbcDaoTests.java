package com.example.webdb.test;

import com.example.webdb.dao.WebdbDao;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/test-applicationContext.xml")
public class WebdbJdbcDaoTests {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    WebdbDao webdbJdbcDao;

    @Autowired
    PlatformTransactionManager transactionManager;

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
        String query = "select * from test_db.users";
        String dbName = "test_db";
        String tableName = "users";

        List<Map<String, Object>> list = webdbJdbcDao.select(query, dbName, tableName);

        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSizeGreaterThan(0);

        for (Map<String, Object> obj : list) {
            log.info("obj is : " + obj);
        }

    }

    @Test
    public void update() {
        String query = "delete from test_db.users";
        String query2 = "select * from test_db.users";
        String dbName = "study_db";
        String tableName = "users";

        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());

        webdbJdbcDao.update(query);
        List<Map<String, Object>> list = webdbJdbcDao.select(query2, dbName, tableName);

        Assertions.assertThat(list).isEmpty();
        transactionManager.rollback(status);
    }

    @Test
    public void createTable() {
        String query = "CREATE TABLE test_db.test_user (\n" +
                "id VARCHAR(10) PRIMARY KEY,\n" +
                "NAME VARCHAR(10) NOT NULL,\n" +
                "PASSWORD VARCHAR(10) NOT NULL\n" +
                ")";
        String query2 = "INSERT INTO test_user (id, NAME, PASSWORD)\n" +
                "VALUES ('boxak','황지원','1234')";
        String query3 = "SELECT * FROM test_user";

        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());

        webdbJdbcDao.update(query);
        webdbJdbcDao.update(query2);
        List<Map<String, Object>> list = webdbJdbcDao.select(query3, "test_db", "test_user");

        Assertions.assertThat(list).isNotEmpty();

        log.info(String.valueOf(list.get(0)));

        transactionManager.rollback(status);
    }

}
