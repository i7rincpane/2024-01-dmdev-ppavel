package ru.nvkz.http.controller;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.nvkz.IntegrationTestBase;
import ru.nvkz.dto.BasketReadDto;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@AutoConfigureMockMvc
@RequiredArgsConstructor
@WithMockUser(username = "test@mail.ru", password = "123", authorities = {"ADMIN", "USER"})
class BasketControllerIT extends IntegrationTestBase {

    private final MockMvc mockMvc;

    @Test
    void findById() throws Exception {
        mockMvc.perform(get("/baskets/" + 2).sessionAttr("basket", new BasketReadDto(2L, null, null, null)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("basket/basket"))
                .andExpect(model().attributeExists("basket"))
                .andExpect(model().attributeExists("basketProducts"))
                .andExpect(model().attribute("basketProducts", hasSize(3)));
    }

    @Test
    void findByIdiSForbidden() throws Exception {
        mockMvc.perform(get("/baskets/" + 2).sessionAttr("basket", new BasketReadDto(1L, null, null, null)))
                .andExpect(status().isForbidden());
    }

    @Test
    void findByIdiSNotFount() throws Exception {
        mockMvc.perform(get("/baskets/" + -1).sessionAttr("basket", new BasketReadDto(-1L, null, null, null)))
                .andExpect(status().isNotFound());
    }

}