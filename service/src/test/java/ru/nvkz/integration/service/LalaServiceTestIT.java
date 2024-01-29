package ru.nvkz.integration.service;

import org.junit.jupiter.api.Test;
import ru.nvkz.service.LalaService;
import ru.nvkz.util.LalaUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LalaServiceTestIT {
    @Test
    void lala() {
        var lalaUtil = new LalaUtil();
        var lalaService  = new LalaService(lalaUtil);
        assertEquals(lalaService.lalala(), "lalala");
    }
}