package ru.nvkz.dto;

import lombok.Value;

@Value
public class LoginDto {

    String email;
    String password;
}