package ru.nvkz.filter;

import lombok.Builder;
import lombok.Value;
import ru.nvkz.entity.Role;

import java.time.LocalDate;
import java.util.List;

@Value
@Builder
public class UserFilter {

    String name;
    String surname;
    LocalDate birthDate;
    List<Role> roles;
    Role role;
}

