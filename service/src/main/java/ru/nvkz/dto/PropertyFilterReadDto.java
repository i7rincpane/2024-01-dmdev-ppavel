package ru.nvkz.dto;

import lombok.Builder;
import lombok.ToString;
import lombok.Value;
import ru.nvkz.entity.TypeValue;

import java.time.Instant;
import java.util.Map;

@Builder
@Value
@ToString(of = "id")
public class PropertyFilterReadDto {

    Long id;
    String name;
    CategoryReadDto category;
    String unit;
    TypeValue dtype;
    Map<StringClassifierReadDto, Integer> stringClassifierValueCounts;
    Map<String, Integer> textValueCounts;
    Map<Integer, Integer> numberValueCounts;
    Map<Double, Integer> floatValueCounts;
    Map<Instant, Integer> dateValueCounts;
    Map<Boolean, Integer> booleanValueCounts;
}
