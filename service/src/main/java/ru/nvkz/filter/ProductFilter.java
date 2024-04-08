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
    List<Integer> produceIds;
    Map<Integer, List<Integer>> propertyIdPropertyValueTextIds = new HashMap<>();
    Map<Integer, Integer> propertyIdPropertyValueNumberFrom = new HashMap<>();
    Map<Integer, Integer> propertyIdPropertyValueNumberBy = new HashMap<>();
    Map<Integer, Double> propertyIdPropertyValueFloatFrom = new HashMap<>();
    Map<Integer, Double> propertyIdPropertyValueFloatBy = new HashMap<>();
}
