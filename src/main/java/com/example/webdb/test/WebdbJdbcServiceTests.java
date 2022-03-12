package com.example.webdb.test;

import com.example.webdb.service.WebdbJdbcService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/test-applicationContext.xml")
public class WebdbJdbcServiceTests {

    @Autowired
    private WebdbJdbcService webdbJdbcService;

    @Test
    public void select() {
        String query = "select * from test_db.users";
        String dbName = "test_db";
        String tableName = "users";

        List<Map<String, Object>> array = webdbJdbcService.select(query, dbName, tableName);

        Assertions.assertThat(array).isNotNull();

        for (int i = 0;i<array.size();i++) {
            log.info(String.valueOf((JSONObject)array.get(i)));
        }
    }

}
