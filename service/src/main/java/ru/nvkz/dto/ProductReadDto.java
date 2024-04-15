package ru.nvkz.dto;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Builder
@Value
public class ProductReadDto {

    Long id;
    Integer code;
    String name;
    String model;
    ProducerReadDto producer;
    BigDecimal price;
    Integer count;
    CategoryReadDto category;

}
