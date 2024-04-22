package ru.nvkz.dto;

import lombok.Builder;
import lombok.Value;

import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Builder
@Value
public class ProductPropertyReadDto<T> {

    Long id;
    ProductReadDto product;
    PropertyReadDto property;
    String textValue;
    Integer numberValue;
    Double floatValue;
    Instant dateValue;
    Boolean booleanValue;
    StringClassifierReadDto stringClassifier;

    public T getValue() {
        return (T) Arrays.asList(textValue,
                        numberValue,
                        floatValue,
                        dateValue,
                        booleanValue,
                        Optional.ofNullable(stringClassifier).map(StringClassifierReadDto::getId).orElse(null)).stream()
                .filter(Objects::nonNull)
                .findFirst().orElse(null);
    }
}





