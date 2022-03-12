package com.example.webdb.service;

import org.json.JSONArray;

import java.util.List;
import java.util.Map;

public interface WebdbService {
    List<Map<String, Object>> select(String query, String dbName, String tableName);
    void update(String query);
}
