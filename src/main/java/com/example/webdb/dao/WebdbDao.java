package com.example.webdb.dao;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public interface WebdbDao {
    List<JSONObject> select(String query, String dbName, String tableName);
    void update(String query);
}
