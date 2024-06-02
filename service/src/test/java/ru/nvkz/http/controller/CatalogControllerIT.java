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
class CatalogControllerIT extends IntegrationTestBase {

    private final MockMvc mockMvc;

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/catalogs")
                        .sessionAttr("basket", new BasketReadDto(1L, null, null, null))
                        .with(SecurityMockMvcRequestPostProcessors
                                .user(new CustomUserDetails(1L,
                                        "test@mail.ru",
                                        "123",
                                        List.of(Role.ADMIN))
                                )
                        ))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("catalog/catalogs"))
                .andExpect(model().attributeExists("catalogs"))
                .andExpect(model().attributeExists("pathElements"))
                .andExpect(model().attribute("catalogs", hasSize(2)))
                .andExpect(model().attribute("pathElements", hasSize(0)));
    }

    @Test
    void findAllByParentId() throws Exception {
        mockMvc.perform(get("/catalogs/" + 1)
                        .sessionAttr("basket", new BasketReadDto(1L, null, null, null))
                        .with(SecurityMockMvcRequestPostProcessors
                                .user(new CustomUserDetails(1L,
                                        "test@mail.ru",
                                        "123",
                                        List.of(Role.ADMIN))
                                )
                        )
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("catalog/catalogs"))
                .andExpect(model().attributeExists("catalogs"))
                .andExpect(model().attributeExists("pathElements"))
                .andExpect(model().attribute("catalogs", hasSize(2)))
                .andExpect(model().attribute("parentId", 1L))
                .andExpect(model().attribute("pathElements", hasSize(1)));
    }
}