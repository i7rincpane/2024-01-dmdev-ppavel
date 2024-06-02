package ru.nvkz.http.controller;

import lombok.RequiredArgsConstructor;
import org.hamcrest.collection.IsCollectionWithSize;
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

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@AutoConfigureMockMvc
@RequiredArgsConstructor
@WithMockUser(username = "test@mail.ru", password = "123", authorities = {"ADMIN", "USER"})
class OrderControllerIT extends IntegrationTestBase {

    private final MockMvc mockMvc;

    @Test
    void createError() throws Exception {
        mockMvc.perform(post("/orders")
                        .sessionAttr("basket", new BasketReadDto(1L, null, null, null))
                )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrlPattern("/baskets/{\\d+}")
                )

                .andExpect(flash().attributeExists("errors"))
                .andExpect(flash().attribute("errors", hasSize(1)));
    }

    @Test
    void create() throws Exception {
        mockMvc.perform(post("/orders")
                        .sessionAttr("basket", new BasketReadDto(2L, null, null, null))
                )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrlPattern("/orders/{\\d+}")
                );
    }

    @Test
    void findAllByAuthenticationUser() throws Exception {
        mockMvc.perform(get("/orders")
                        .with(SecurityMockMvcRequestPostProcessors
                                .user(new CustomUserDetails(1L,
                                        "test@mail.ru",
                                        "123",
                                        List.of(Role.ADMIN))
                                )
                        )
                )
                .andExpectAll(
                        status().is2xxSuccessful(),
                        view().name("order/orders"),
                        model().attributeExists("orderStatuses"),
                        model().attributeExists("orders"),
                        model().attribute("orders", hasSize(1))
                );
    }

    @Test
    void findById() throws Exception {
        mockMvc.perform(get("/orders/" + 3).with(SecurityMockMvcRequestPostProcessors
                        .user(new CustomUserDetails(1L,
                                "test@mail.ru",
                                "123",
                                List.of(Role.ADMIN))
                        )
                ))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("order/order"))
                .andExpect(model().attributeExists("basket"))
                .andExpect(model().attributeExists("order"))
                .andExpect(model().attributeExists("orderProducts"))
                .andExpect(model().attributeExists("orderProducts"))
                .andExpect(model().attribute("orderProducts", IsCollectionWithSize.hasSize(2)));
    }

    @Test
    void findByIdIsNotFound() throws Exception {
        mockMvc.perform(get("/orders/" + 2).with(SecurityMockMvcRequestPostProcessors
                        .user(new CustomUserDetails(1L,
                                "test@mail.ru",
                                "123",
                                List.of(Role.ADMIN))
                        )
                ))
                .andExpect(status().isNotFound());
    }

}