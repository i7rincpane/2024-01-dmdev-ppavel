package ru.nvkz.dto;

import lombok.Value;
import ru.nvkz.util.WithValue;

@Value
public class PropertyValueReadDto implements WithValue {

    Long id;
    String value;
}