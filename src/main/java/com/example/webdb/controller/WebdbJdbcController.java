package com.example.webdb.controller;

import com.example.webdb.service.WebdbJdbcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/jdbc")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class WebdbJdbcController {

    @Autowired
    WebdbJdbcService webdbJdbcService;

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> get(String query, String dbName, String tableName) {

        List<Map<String, Object>> list = webdbJdbcService.select(query, dbName, tableName);

        return new ResponseEntity<List<Map<String, Object>>>(list, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> post(String query) {
        webdbJdbcService.update(query);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Void> put(String query) {
        webdbJdbcService.update(query);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(String query) {
        webdbJdbcService.update(query);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Void> create(String query) {
        webdbJdbcService.update(query);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
