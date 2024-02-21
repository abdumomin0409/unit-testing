package com.coreteam.languageservice.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JsonUtils {
    private final ObjectMapper objectMapper;
    private final JdbcTemplate jdbcTemplate;

    public Map<String, Object> parseMapFromJson(String result) {
        try {
            JsonNode jsonNode = objectMapper.readTree(result);
            return objectMapper.convertValue(jsonNode, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String jsonFromObject(Object o) {
        try {
            return objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeConnection() {
        // Bog'lanishni yopish
        DataSource dataSource = jdbcTemplate.getDataSource();
        if (dataSource != null) {
            try {
                dataSource.getConnection().close();
            } catch (SQLException e) {
                // Bog'lanishni yopishda xatolik yuz berdi
                e.printStackTrace();
            }
        }
    }

}
