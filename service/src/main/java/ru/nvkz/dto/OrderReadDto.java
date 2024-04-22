package ru.nvkz.dto;

import lombok.Value;
import ru.nvkz.entity.OrderStatus;

import java.math.BigDecimal;
import java.time.Instant;

@Value
public class OrderReadDto {

    Long id;
    UserReadDto user;
    Instant createdAt;
    Instant updatedAt;
    BigDecimal sum;
    OrderStatus orderStatus;
    Integer count;
}
