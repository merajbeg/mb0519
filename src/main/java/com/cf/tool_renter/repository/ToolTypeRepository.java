package com.cf.tool_renter.repository;

import java.util.Map;

import com.cf.tool_renter.value_object.ToolType;


public interface ToolTypeRepository {
    Map<String, ToolType> getToolTypes();      //readToolTypesFromFile(TOOL_TYPES_FILE_PATH); getTools();
    
}
