package ru.nvkz.integration;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import ru.nvkz.Main;


public class ExampleIT {
    @Test
    public void test2() {
        assertTrue(Main.get().equals(1));
    }
}
