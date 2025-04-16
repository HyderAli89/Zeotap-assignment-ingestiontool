package com.ingestiontool.ingestiontool.service;

import com.ingestiontool.ingestiontool.util.CSVReaderUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.mockito.Mockito.*;

import org.mockito.MockedStatic;

@ExtendWith(MockitoExtension.class)
public class IngestionServiceTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    private SchemaService schemaService;

    @InjectMocks
    private IngestionService ingestionService;

    @Test
    public void testIngestFlatFileToClickHouse() {
        List<List<String>> mockRows = List.of(
                List.of("1", "Alice"),
                List.of("2", "Bob")
        );

        try (MockedStatic<CSVReaderUtil> mockedStatic = mockStatic(CSVReaderUtil.class)) {
            mockedStatic.when(() -> CSVReaderUtil.readRows("mockFile.csv")).thenReturn(mockRows);

            ingestionService.ingestFlatFileToClickHouse("mockFile.csv", "users");

            verify(jdbcTemplate, times(2)).update(anyString());
        }
    }
}


