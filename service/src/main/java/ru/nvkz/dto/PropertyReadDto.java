package ru.nvkz.dto;

import lombok.Builder;
import lombok.Value;

import java.util.Map;

@Builder
@Value
public class PropertyReadDto {

    private String id;
    private String name;
    private String unit;
    private String dtype;
    private Map<String, Integer> values;
}
