package ru.nvkz.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ProductTypeReadDto {

    Integer id;
    String name;
    ProductTypeReadDto parentDto;
}
