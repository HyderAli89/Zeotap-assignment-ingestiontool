package com.ingestiontool.ingestiontool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.ingestiontool.ingestiontool.util.CSVReaderUtil;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class IngestionService {

    private final JdbcTemplate jdbcTemplate;
    private final SchemaService schemaService;

    @Autowired
    public IngestionService(JdbcTemplate jdbcTemplate, SchemaService schemaService) {
        this.jdbcTemplate = jdbcTemplate;
        this.schemaService = schemaService;
    }

    // Ingest data from ClickHouse to Flat File (CSV) 

    public void ingestClickHouseToFlatFile(String tableName, List<String> selectedColumns, String filePath) throws IOException {
    // Log the inputs
    System.out.println("Ingesting data from table: " + tableName);
    System.out.println("Selected Columns: " + selectedColumns);
    System.out.println("File path: " + filePath);

    // Create the SQL query string
    String columns = String.join(", ", selectedColumns);
    String query = "SELECT " + columns + " FROM " + tableName;

    // Log the query
    System.out.println("Executing query: " + query);

    List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);

    // Log number of rows fetched
    System.out.println("Fetched " + rows.size() + " rows.");

    // Create a file writer to output data
    FileWriter writer = new FileWriter(filePath);

    // Write header (column names)
    writer.append(String.join(",", selectedColumns)).append("\n");
    System.out.println("Writing header: " + String.join(",", selectedColumns));

    // Write data rows
    for (Map<String, Object> row : rows) {
        List<String> values = selectedColumns.stream()
            .map(col -> row.get(col) != null ? row.get(col).toString() : "")
            .toList();
        writer.append(String.join(",", values)).append("\n");

        // Log each row's data
        System.out.println("Writing row: " + String.join(",", values));
    }

    writer.close();
    System.out.println("Data ingestion completed and file written to: " + filePath);
}
    
    // Ingest data from Flat File to ClickHouse
    public void ingestFlatFileToClickHouse(String filePath, String tableName) {
        List<List<String>> rows = CSVReaderUtil.readRows(filePath);

        for (List<String> row : rows) {
            // Add quotes around each value to handle string and escape issues
            String values = row.stream()
                    .map(val -> "'" + val.replace("'", "''") + "'")
                    .reduce((a, b) -> a + ", " + b)
                    .orElse("");

            String query = "INSERT INTO " + tableName + " VALUES (" + values + ")";
            jdbcTemplate.update(query);
        }
    }

    public void ingestFlatFileToClickHouse(String filePath, String tableName, List<String> selectedColumns) throws IOException, CsvValidationException {
    List<Map<String, String>> rows = CSVReaderUtil.readData(filePath, selectedColumns); // utility method you can define

    String columnsJoined = String.join(", ", selectedColumns);
    String valuePlaceholders = selectedColumns.stream().map(col -> "?").collect(Collectors.joining(", "));

    String insertQuery = "INSERT INTO " + tableName + " (" + columnsJoined + ") VALUES (" + valuePlaceholders + ")";

    jdbcTemplate.batchUpdate(insertQuery, new BatchPreparedStatementSetter() {
        @Override
        public void setValues(PreparedStatement ps, int i) throws SQLException {
            Map<String, String> row = rows.get(i);
            for (int j = 0; j < selectedColumns.size(); j++) {
                ps.setString(j + 1, row.get(selectedColumns.get(j)));
            }
        }

        @Override
        public int getBatchSize() {
            return rows.size();
        }
    });
}

}
