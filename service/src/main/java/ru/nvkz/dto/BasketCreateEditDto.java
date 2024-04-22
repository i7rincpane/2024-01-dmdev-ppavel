package ru.nvkz.dto;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Builder
@Value
public class BasketCreateEditDto {

    Long userId;
    BigDecimal sum;
    Integer count;
}