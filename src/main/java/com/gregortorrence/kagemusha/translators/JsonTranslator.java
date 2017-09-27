package com.gregortorrence.kagemusha.translators;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JsonTranslator implements Translator {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final TypeReference<LinkedHashMap<String,Object>> mapTypeRef = new TypeReference<LinkedHashMap<String,Object>>() {};

    @Override
    public void translate(File inputFile, File outputFile) throws IOException {
        Map<String,Object> map = objectMapper.readValue(inputFile, mapTypeRef);
        translate(map);
        objectMapper.writeValue(outputFile, map);
    }

    private void translate(Map<String,Object> map) {
        map.keySet().forEach(key -> {
            Object value = map.get(key);
            if (value instanceof String) {
                map.put(key, Kagemusha.translate((String) map.get(key)));
            } else if (value instanceof List) {
                map.put(key, translate((List)map.get(key)));
            } else if (value instanceof Map){
                translate((Map<String, Object>)map.get(key));
            } else {
                System.err.println("Skipping value " + value + " of type " + value.getClass());
            }
        });
    }

    private List<String> translate(List<String> list) {
        return list.stream().map(Kagemusha::translate).collect(Collectors.toList());
    }

}
