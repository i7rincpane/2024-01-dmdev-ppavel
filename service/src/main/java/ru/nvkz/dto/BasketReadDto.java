package ru.nvkz.dto;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class BasketReadDto {

    Long id;
    UserReadDto userReadDto;
    BigDecimal sum;
    Integer count;
}
