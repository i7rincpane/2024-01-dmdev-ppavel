package ru.nvkz.dto;

import lombok.Value;
import ru.nvkz.entity.TypeValue;

@Value
public class PropertyCreateEditDto {

    String name;
    Long categoryId;
    String unit;
    TypeValue dtype;
}
