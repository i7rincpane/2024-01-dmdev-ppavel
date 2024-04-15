package ru.nvkz.dto;

import lombok.Value;

@Value
public class StringClassifierReadDto {

    Long id;
    String name;
    PropertyReadDto property;
}
