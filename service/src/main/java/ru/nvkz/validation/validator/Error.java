package ru.nvkz.validation.validator;

import lombok.Value;

import java.io.Serializable;
import java.util.Map;

@Value(staticConstructor = "of")
public class Error {
    Integer code;
    String message;
    Map<? extends Serializable, String> details;
}