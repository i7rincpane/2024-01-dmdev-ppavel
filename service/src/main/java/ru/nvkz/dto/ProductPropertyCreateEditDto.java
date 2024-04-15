package ru.nvkz.dto;

import lombok.Builder;
import lombok.Value;

import java.time.Instant;

@Builder
@Value
public class ProductPropertyCreateEditDto {

    Long id;
    Long productId;
    Long propertyId;
    String textValue;
    Integer numberValue;
    Double floatValue;
    Instant dateValue;
    Boolean booleanValue;
    Long stringClassifierId;
}