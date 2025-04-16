package com.ingestiontool.ingestiontool.service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

public class YourService {

    private final JdbcTemplate jdbcTemplate = new JdbcTemplate();

    private static final Logger logger = LoggerFactory.getLogger(YourService.class);

    public void ingestClickHouseToFlatFile(String tableName, List<String> selectedColumns, String filePath) throws IOException {
        System.out.println(tableName + "" + selectedColumns + "" + filePath);
        String columns = String.join(", ", selectedColumns);
        String query = "SELECT " + columns + " FROM " + tableName;
    
        // Call queryForList on the instance of JdbcTemplate
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
    
        FileWriter writer = new FileWriter(filePath);
    
        // Write header (column names)
        writer.append(String.join(",", selectedColumns)).append("\n");
    
        // Write data rows
        for (Map<String, Object> row : rows) {
            List<String> values = selectedColumns.stream()
                .map(col -> row.get(col) != null ? row.get(col).toString() : "")
                .toList();
            writer.append(String.join(",", values)).append("\n");
        }
    
        writer.close();
    }
    
}
