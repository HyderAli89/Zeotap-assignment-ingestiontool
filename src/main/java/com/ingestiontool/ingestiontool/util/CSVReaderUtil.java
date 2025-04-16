package com.ingestiontool.ingestiontool.util;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVReaderUtil {

    // Reads only the header (first row)
    public static List<String> readColumns(String filePath) {
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] header = reader.readNext();
            return header != null ? Arrays.asList(header) : List.of();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    // Reads all data rows (excluding header)
    public static List<List<String>> readRows(String filePath) {
        List<List<String>> rows = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] line;
            boolean isFirstRow = true;

            while ((line = reader.readNext()) != null) {
                if (isFirstRow) {
                    isFirstRow = false;
                    continue;
                }
                rows.add(Arrays.asList(line));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rows;
    }

    public static List<Map<String, String>> readData(String filePath, List<String> selectedColumns) throws IOException, CsvValidationException {
        List<Map<String, String>> result = new ArrayList<>();
    
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] headers = reader.readNext();
            if (headers == null) return result;
    
            Map<String, Integer> colIndex = new HashMap<>();
            for (int i = 0; i < headers.length; i++) {
                colIndex.put(headers[i].trim(), i);
            }
    
            String[] line;
            while ((line = reader.readNext()) != null) {
                Map<String, String> row = new HashMap<>();
                for (String col : selectedColumns) {
                    int index = colIndex.getOrDefault(col, -1);
                    row.put(col, (index != -1 && index < line.length) ? line[index].trim() : "");
                }
                result.add(row);
            }
        }
    
        return result;
    }
    
}
