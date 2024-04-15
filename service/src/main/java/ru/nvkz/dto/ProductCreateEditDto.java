package ru.nvkz.dto;

import lombok.Value;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;


@Value
public class ProductCreateEditDto {

    Integer code;
    String name;
    String model;
    Long producerId;
    BigDecimal price;
    Integer count;
    Long categoryId;

    Map<Long, Long> propertyIdStringClassifierId = new HashMap<>();
    Map<Long, Boolean> propertyIdBooleanValue = new HashMap<>();
    Map<Long, Instant> propertyIdDateValue = new HashMap<>();
    Map<Long, Double> propertyIdFloatValue = new HashMap<>();
    Map<Long, Integer> propertyIdNumberValue = new HashMap<>();
    Map<Long, String> propertyIdTextValue = new HashMap<>();
}
