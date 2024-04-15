package ru.nvkz.filter;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Value
@Builder
public class ProductFilter {

    BigDecimal priceFrom;
    BigDecimal priceBy;
    List<Long> produceIds;
    Map<Integer, List<Long>> propertyIdStringClassifierIds = new HashMap<>();
    Map<Integer, Integer> propertyIdNumberValueFrom = new HashMap<>();
    Map<Integer, Integer> propertyIdNumberValueBy = new HashMap<>();
    Map<Integer, Double> propertyIdFloatValueFrom = new HashMap<>();
    Map<Integer, Double> propertyIdFloatValueBy = new HashMap<>();
}
