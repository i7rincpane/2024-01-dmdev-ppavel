package ru.nvkz.filter;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.List;

@Value
@Builder
public class ProductFilter {

    String color;
    String producer;
    BigDecimal price;
    Integer productTypeId;
    List<Long> productPropertyIdBatch;
}
