package ru.nvkz.http.controller;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import ru.nvkz.IntegrationTestBase;
import ru.nvkz.dto.BasketReadDto;
import ru.nvkz.dto.CustomUserDetails;
import ru.nvkz.entity.Role;

import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@AutoConfigureMockMvc
@RequiredArgsConstructor
@WithMockUser(username = "test@mail.ru", password = "123", authorities = {"ADMIN", "USER"})
class ProductControlerIT extends IntegrationTestBase {

    private final MockMvc mockMvc;

    @Test
    void findByid() throws Exception {
        mockMvc.perform(get("/products/" + 2)
                        .sessionAttr("basket", new BasketReadDto(2L, null, null, null))
                        .with(SecurityMockMvcRequestPostProcessors
                                .user(new CustomUserDetails(1L,
                                        "test@mail.ru",
                                        "123",
                                        List.of(Role.ADMIN))
                                )
                        ))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("catalog/product"))
                .andExpect(model().attributeExists("productRead"))
                .andExpect(model().attributeExists("productCreateEdit"))
                .andExpect(model().attributeExists("productProperties"))
                .andExpect(model().attributeExists("propertyIdStringClassifiers"))
                .andExpect(model().attributeExists("producers"))
                .andExpect(model().attribute("productProperties", hasSize(5)));
    }

    @Test
    void update() {
    }

    @Test
    void create() {
    }

    @Test
    void delete() {
    }

    @Test
    void showProductCreateForm() {
    }
}