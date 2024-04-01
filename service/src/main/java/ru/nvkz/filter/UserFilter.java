package ru.nvkz.filter;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class UserFilter {
    String name;
    String surname;
    LocalDate birthDate;
}