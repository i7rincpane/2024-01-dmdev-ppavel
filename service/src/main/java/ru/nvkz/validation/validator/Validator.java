package ru.nvkz.validation.validator;

public interface Validator<T> {

    ValidationResult validate(T object);
}