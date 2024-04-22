package ru.nvkz.dto;

import lombok.Value;

import java.math.BigDecimal;


@Value
public class BasketProductCreateEditDto {

    Long productId;
    Long basketId;
    Integer count;
    BigDecimal sum;
    Boolean isActive;
}
