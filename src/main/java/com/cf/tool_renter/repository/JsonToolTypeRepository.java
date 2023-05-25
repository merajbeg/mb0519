
package com.cf.tool_renter.repository;

import com.cf.tool_renter.value_object.ToolType;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Repository
public class JsonToolTypeRepository implements ToolTypeRepository {
    private final ObjectMapper objectMapper;

    public JsonToolTypeRepository(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Map<String, ToolType> getToolTypes() {
        try {
            ClassPathResource resource = new ClassPathResource("tool_types.json");
            InputStream inputStream = resource.getInputStream();

            Map<String, ToolType> toolTypeMap = objectMapper.readValue(inputStream, new TypeReference<Map<String, ToolType>>() {});
            
            return toolTypeMap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
