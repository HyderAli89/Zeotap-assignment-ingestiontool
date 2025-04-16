package com.ingestiontool.ingestiontool.controller;

import com.ingestiontool.ingestiontool.service.IngestionService;
import com.ingestiontool.ingestiontool.service.SchemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/ingestion")
public class IngestionController {

    private final IngestionService ingestionService;
    private final SchemaService schemaService;

    @Autowired
    public IngestionController(IngestionService ingestionService, SchemaService schemaService) {
        this.ingestionService = ingestionService;
        this.schemaService = schemaService;
    }

    @GetMapping("/tables")
    public List<String> getClickHouseTables() {
        return schemaService.getTablesFromClickHouse();
    }

    @GetMapping("/columns")
    public List<String> getClickHouseColumns(@RequestParam String tableName) {
        return schemaService.getColumnsFromClickHouse(tableName);
    }

    @PostMapping("/clickhouse-to-file")
    public String ingestClickHouseToFlatFile(@RequestParam String tableName, @RequestParam List<String> selectedColumns, @RequestParam String filePath) throws IOException {
        ingestionService.ingestClickHouseToFlatFile(tableName, selectedColumns, filePath);
        
        return "Data ingestion complete!";
    }

    
}
