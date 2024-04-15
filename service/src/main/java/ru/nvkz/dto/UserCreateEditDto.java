package ru.nvkz.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;
import lombok.experimental.FieldNameConstants;
import ru.nvkz.entity.Role;
import ru.nvkz.validation.group.CreateAction;

import java.time.LocalDate;

@Value
@FieldNameConstants
public class UserCreateEditDto {

    String email;
    @NotBlank(groups = CreateAction.class)
    String password;
    LocalDate birthDate;
    String name;
    String surname;
    String patronimic;
    Role role;
    String telephone;
}