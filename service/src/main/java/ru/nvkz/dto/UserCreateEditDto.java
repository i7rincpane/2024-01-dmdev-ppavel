package ru.nvkz.dto;

import lombok.Value;
import lombok.experimental.FieldNameConstants;
import ru.nvkz.entity.Role;

import java.time.LocalDate;

@Value
@FieldNameConstants
public class UserCreateEditDto {

    String email;
    String password;
    LocalDate birthDate;
    String name;
    String surname;
    String patronimic;
    Role role;
    String telephone;
}