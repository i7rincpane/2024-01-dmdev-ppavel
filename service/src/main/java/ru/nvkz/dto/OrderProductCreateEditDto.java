package ru.nvkz.dto;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class OrderProductCreateEditDto {

    Long productId;
    Long orderId;
    Integer count;
    BigDecimal sum;
}
