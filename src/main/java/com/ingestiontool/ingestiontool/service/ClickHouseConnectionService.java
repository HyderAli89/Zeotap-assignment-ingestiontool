package com.ingestiontool.ingestiontool.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
public class ClickHouseConnectionService {

    @Value("${spring.datasource.url}")
    private String clickhouseUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    private final JdbcTemplate jdbcTemplate;

    public ClickHouseConnectionService(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // Connect and test the connection (just a simple query to test the connection)
    public boolean testConnection() {
        try {
            String query = "SELECT 1";  // A simple query to test the connection
            jdbcTemplate.execute(query);
            return true;  // If successful
        } catch (Exception e) {
            e.printStackTrace();
            return false;  // If there's any error
        }
    }
}
