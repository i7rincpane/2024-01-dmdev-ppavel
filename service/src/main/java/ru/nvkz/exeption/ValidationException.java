package ru.nvkz.exeption;

import lombok.Getter;
import ru.nvkz.validation.validator.Error;

import java.util.List;

public class ValidationException extends RuntimeException {

    @Getter
    private final List<Error> errors;

    public ValidationException(List<Error> errors) {
        this.errors = errors;
    }
    public ValidationException(Error error) {
        this.errors = List.of(error);
    }

}