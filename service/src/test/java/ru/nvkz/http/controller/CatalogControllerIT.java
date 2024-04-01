package ru.nvkz.http.controller;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import ru.nvkz.IntegrationTestBase;


import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@AutoConfigureMockMvc
@RequiredArgsConstructor
class CatalogControllerIT extends IntegrationTestBase {

    private final MockMvc mockMvc;

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/catalogs"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("catalog/catalogs"))
                .andExpect(model().attributeExists("catalogs"))
                .andExpect(model().attributeExists("pathElements"))
                .andExpect(model().attribute("catalogs", hasSize(2)))
                .andExpect(model().attribute("pathElements", hasSize(0)));
    }

    @Test
    void findAllByParentId() throws Exception {
        mockMvc.perform(get("/catalogs/" + 1))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("catalog/catalogs"))
                .andExpect(model().attributeExists("catalogs"))
                .andExpect(model().attributeExists("pathElements"))
                .andExpect(model().attribute("catalogs", hasSize(2)))
                .andExpect(model().attribute("parentId", 1))
                .andExpect(model().attribute("pathElements", hasSize(1)));
    }
}