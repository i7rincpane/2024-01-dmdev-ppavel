package ru.nvkz.dto;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class CategoryReadDto {

    Long id;
    String name;
    CategoryReadDto parent;
}
