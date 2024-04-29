package ru.nvkz.dto;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class BasketProductReadDto {

    Long id;
    ProductReadDto product;
    BasketReadDto basket;
    Integer count;
    BigDecimal sum;
    Boolean isSelected;
}
