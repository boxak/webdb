package com.example.webdb.service;

import com.example.webdb.dao.WebdbJdbcDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class WebdbJdbcService implements WebdbService {

    @Autowired
    private WebdbJdbcDao dao;

    @Override
    public List<Map<String, Object>> select(String query, String dbName, String tableName) {
        List<Map<String, Object>> jsonObjects = dao.select(query, dbName, tableName);

        return jsonObjects;
    }

    @Override
    public void update(String query) {
        dao.update(query);
    }
}
