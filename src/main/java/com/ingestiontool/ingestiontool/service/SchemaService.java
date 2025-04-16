package com.ingestiontool.ingestiontool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ingestiontool.ingestiontool.util.CSVReaderUtil;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@Service
public class SchemaService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SchemaService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Fetch all tables from ClickHouse
    public List<String> getTablesFromClickHouse() {
        String query = "SHOW TABLES"; 
        return jdbcTemplate.queryForList(query, String.class);
    }

    // Fetch columns of a specific table from ClickHouse
    public List<String> getColumnsFromClickHouse(String tableName) {
        String query = "DESCRIBE " + tableName;
        System.out.println("query -->"+ query);
        
        return jdbcTemplate.queryForList(query, String.class);
    }

    // For flat file, we can use simple Java libraries to read column names (assuming CSV for now)
    public List<String> getColumnsFromFlatFile(String filePath) {
        return CSVReaderUtil.readColumns(filePath);
    }
}
