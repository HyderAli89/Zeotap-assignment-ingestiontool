package com.ingestiontool.ingestiontool.service;

import org.springframework.stereotype.Service;

@Service
public class ErrorHandlingService {

    // Handle database connection errors
    public void handleConnectionError(Exception e) {
        System.out.println("Connection failed: " + e.getMessage());
    }

    // Handle ingestion errors
    public void handleIngestionError(Exception e) {
        System.out.println("Ingestion failed: " + e.getMessage());
    }

    // Log completion of ingestion
    public void logCompletion(int recordCount) {
        System.out.println("Ingestion completed. Total records: " + recordCount);
    }
}
