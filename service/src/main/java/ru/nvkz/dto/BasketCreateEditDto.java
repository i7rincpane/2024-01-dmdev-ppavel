package ru.nvkz.dto;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;


@Value
public class BasketCreateEditDto {

    Long userId;
    BigDecimal sum;
    Integer count;
}