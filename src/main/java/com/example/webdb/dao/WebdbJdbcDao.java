package com.example.webdb.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class WebdbJdbcDao implements WebdbDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public  List<Map<String, Object>> select(String query, String dbName, String tableName) {

        String[] columnNames = getColumnNames(dbName, tableName);

        return this.jdbcTemplate.query(query,
                (rs, rowNum) -> {
                    Map<String, Object> map = new HashMap<>();
                    for (int i = 0;i<columnNames.length;i++) {
                        map.put(columnNames[i], rs.getObject(columnNames[i]));
                    }
                    return map;
                }
        );
    }

    @Override
    public void update(String query) {
        this.jdbcTemplate.update(query);
    }

    private String[] getColumnNames(String dbName, String tableName) {
        String query = "SELECT COLUMN_NAME\n" +
                "FROM INFORMATION_SCHEMA.COLUMNS\n" +
                "WHERE TABLE_SCHEMA='" + dbName +"'\n" +
                "AND TABLE_NAME='"+tableName+"'";
        List<String> columnNames = this.jdbcTemplate.query(query,
                (rs, rowNum) -> rs.getString(1)
        );

        String[] result = new String[columnNames.size()];

        for (int i = 0;i<columnNames.size();i++) {
            result[i] = columnNames.get(i);
        }

        return result;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
}
