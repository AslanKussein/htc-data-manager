package kz.dilau.htcdatamanager.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kz.dilau.htcdatamanager.domain.enums.RealPropertyFileType;

import javax.persistence.AttributeConverter;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class FilesMapConverter implements AttributeConverter<Map<RealPropertyFileType, Set<String>>, String> {
    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Map<RealPropertyFileType, Set<String>> filesMap) {
        String json = null;
        try {
            json = OBJECT_MAPPER.writeValueAsString(filesMap);
        } catch (final JsonProcessingException e) {
//            logger.error("JSON writing error", e);
        }
        return json;
    }

    @Override
    public Map<RealPropertyFileType, Set<String>> convertToEntityAttribute(String json) {
        Map<RealPropertyFileType, Set<String>> filesMap = null;
        try {
            filesMap = OBJECT_MAPPER.readValue(json, Map.class);
        } catch (final IOException e) {
//            logger.error("JSON reading error", e);
        }
        return filesMap;
    }
}
