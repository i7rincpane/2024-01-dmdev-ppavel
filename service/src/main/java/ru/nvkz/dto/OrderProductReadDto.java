package ru.nvkz.dto;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class OrderProductReadDto {

    Long id;
    ProductReadDto product;
    OrderReadDto order;
    Integer count;
    BigDecimal sum;
}
