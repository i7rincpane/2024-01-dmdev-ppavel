package ru.nvkz.entity;

import org.springframework.security.core.GrantedAuthority;

import java.util.Locale;
import java.util.ResourceBundle;

public enum Role implements GrantedAuthority {

    USER("User"),
    ADMIN("Admin");

    public final String value;

    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", new Locale("ru"));

    Role(String value) {
        this.value = value;
    }

    // TODO : 22.04.2024 сто процентов это не так делается?
    public static Role[] values(Locale locale) {
        resourceBundle = ResourceBundle.getBundle("messages", locale);
        return Role.values();
    }

    @Override
    public String getAuthority() {
        return name();
    }

    @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("user.role."
                + name().toLowerCase());
        return displayStatusString;
    }

}
