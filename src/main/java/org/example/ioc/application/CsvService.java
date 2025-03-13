package org.example.ioc.application;

import org.example.ioc.utils.container.annotations.Component;

import java.util.Map;

@Component
public class CsvService {
    public String createCsv(Map<String, Object> map) {
        return String.join(",", map.keySet())
                + "\n"
                + String.join(",", map.values().stream().map(Object::toString).toList());
    }
}
