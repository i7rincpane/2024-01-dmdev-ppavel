package ru.nvkz.dto;

import lombok.Builder;
import lombok.Value;
import ru.nvkz.entity.TypeValue;

import java.util.Map;

@Builder
@Value
public class PropertyReadDto {

     Integer id;
     String name;
     String unit;
     TypeValue dtype;
     Map<String, Integer> values;
}
