package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

public class JsonUtils {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static JsonNode getTestData(String filePath) {
        try {
            return mapper.readTree(new File(filePath));
        } catch (Exception e) {
            throw new RuntimeException("Failed to read JSON file: " + filePath);
        }
    }
}
