package com.ingestiontool.ingestiontool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class ClickHouseService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void testConnection() {
        try {
            String sql = "SELECT 1";  
            Integer result = jdbcTemplate.queryForObject(sql, Integer.class);
            System.out.println("Result of test query: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
