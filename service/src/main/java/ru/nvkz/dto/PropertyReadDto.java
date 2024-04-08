package ru.nvkz.dto;

import lombok.Builder;
import lombok.ToString;
import lombok.Value;
import ru.nvkz.entity.TypeValue;

import java.util.Map;

@Builder
@Value
@ToString(of = "id")
public class PropertyReadDto {

    Integer id;
    String name;
    CategoryReadDto category;
    String unit;
    TypeValue dtype;
    Map<PropertyValueReadDto, Integer> propertyValueProductCounts;
}
