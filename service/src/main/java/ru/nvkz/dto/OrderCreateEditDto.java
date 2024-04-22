package ru.nvkz.dto;

import lombok.Builder;
import lombok.Value;
import ru.nvkz.entity.OrderStatus;

import java.math.BigDecimal;
import java.time.Instant;

@Builder
@Value
public class OrderCreateEditDto {

    Long userId;
    Long basketId;
    Instant createdAt;
    Instant updatedAt;
    BigDecimal sum;
    OrderStatus orderStatus;
    Integer count;
}