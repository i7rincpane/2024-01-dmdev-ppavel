package ru.nvkz.filter;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.List;

@Value
@Builder
public class ProductFilter {

    Integer productTypeId;
    List<String> producerNames;
    BigDecimal priceFrom;
    BigDecimal priceBy;
    List<Long> propertyIdBatch;
}
