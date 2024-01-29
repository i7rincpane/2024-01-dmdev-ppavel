package ru.nvkz.util;

public final class HelloUtil {
    private HelloUtil() {
    }

    public static String helloByName(String name) {
        return String.format("Привет %s", name);
    }
}
