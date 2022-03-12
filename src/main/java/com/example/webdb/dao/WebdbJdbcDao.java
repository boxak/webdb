package com.example.webdb.dao;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class WebdbJdbcDao implements WebdbDao {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<JSONObject> select(String query, String dbName, String tableName) {

        String[] columnNames = getColumnNames(dbName, tableName);

        return this.jdbcTemplate.query(query,
                (rs, rowNum) -> {
                    JSONObject obj = new JSONObject();
                    for (int i = 0;i<columnNames.length;i++) {
                        obj.put(columnNames[i], rs.getObject(columnNames[i]));
                    }
                    return obj;
                }
        );
    }

    @Override
    public void update(String query) {

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
