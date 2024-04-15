package ru.nvkz.dto;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class StringClassifierCreateEditDto {

    String name;
    Long propertyId;
}