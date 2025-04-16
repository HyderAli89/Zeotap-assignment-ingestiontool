package com.ingestiontool.ingestiontool.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class SchemaServiceTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private SchemaService schemaService;

    @Test
    public void testGetTablesFromClickHouse() {
        List<String> mockTables = List.of("users", "orders");

        when(jdbcTemplate.queryForList("SHOW TABLES", String.class)).thenReturn(mockTables);

        List<String> result = schemaService.getTablesFromClickHouse();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("users", result.get(0));
        verify(jdbcTemplate).queryForList("SHOW TABLES", String.class);
    }
}
