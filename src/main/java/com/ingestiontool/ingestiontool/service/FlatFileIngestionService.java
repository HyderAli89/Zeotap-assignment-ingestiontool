package com.ingestiontool.ingestiontool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.ingestiontool.ingestiontool.util.CSVReaderUtil;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class FlatFileIngestionService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FlatFileIngestionService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void ingestFlatFileToClickHouse(String filePath, String tableName, List<String> selectedColumns)
        throws IOException, CsvValidationException {

    List<Map<String, String>> dataRows = CSVReaderUtil.readData(filePath, selectedColumns);

    String placeholders = String.join(", ", selectedColumns).replaceAll("[^,]+", "?");
    String insertQuery = "INSERT INTO " + tableName + " (" + String.join(", ", selectedColumns) + ") VALUES (" + placeholders + ")";

    jdbcTemplate.batchUpdate(insertQuery, dataRows, 1000, (ps, row) -> {
        for (int i = 0; i < selectedColumns.size(); i++) {
            ps.setString(i + 1, row.getOrDefault(selectedColumns.get(i), ""));
        }
    });
}

}
