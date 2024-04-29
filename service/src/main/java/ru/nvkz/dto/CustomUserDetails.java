package ru.nvkz.dto;

import lombok.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Value
public class CustomUserDetails extends User {
    Long id;

    public CustomUserDetails(Long id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.id = id;
    }
}
