package ru.nvkz.dto;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class ProductReadDto {

    private Long id;
    private Integer code;
    private String name;
    private String model;
    private ProducerReadDto producer;
    private BigDecimal price;
    private Integer count;
    private CategoryReadDto categoryRead;
}
