package ru.nvkz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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