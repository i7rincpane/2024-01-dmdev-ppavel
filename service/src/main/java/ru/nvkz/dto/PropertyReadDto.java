package ru.nvkz.dto;

import lombok.Builder;
import lombok.Value;
import ru.nvkz.entity.TypeValue;

@Builder
@Value
public class PropertyReadDto {

    Long id;
    String name;
    CategoryReadDto category;
    String unit;
    TypeValue dtype;
}