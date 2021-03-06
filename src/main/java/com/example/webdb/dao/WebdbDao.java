package com.example.webdb.dao;

import java.util.List;
import java.util.Map;

public interface WebdbDao {
    List<Map<String, Object>> select(String query, String dbName, String tableName);
    void update(String query);
}
