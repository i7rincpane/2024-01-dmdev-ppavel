package ru.nvkz.dto;

import lombok.Value;
import ru.nvkz.entity.Role;

import java.time.LocalDate;

@Value
public class UserReadDto {

    Long id;
    String email;
    LocalDate birthDate;
    String name;
    String surname;
    String patronimic;
    Role role;
    String telephone;
}