package ru.nvkz.http.controller;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.nvkz.IntegrationTestBase;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class OrderControllerIT extends IntegrationTestBase {

    private final MockMvc mockMvc;

    @Test
    @WithMockUser(username = "test@mail.ru", password = "123", authorities = {"ADMIN", "USER"})
    void create() throws Exception {
        mockMvc.perform(post("/orders")
                        .param("basketId", "1")
                )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrlPattern("/baskets/{\\d+}")
                )

                .andExpect(flash().attributeExists("errors"));
//              TODO: неполучается проверить атребут который передаю в редирект
//                .andExpect(flash().attribute("errors", hasSize(1)));
    }
}