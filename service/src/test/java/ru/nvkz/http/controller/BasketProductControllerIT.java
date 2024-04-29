package ru.nvkz.http.controller;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.nvkz.IntegrationTestBase;
import ru.nvkz.dto.BasketProductCreateEditDto;
import ru.nvkz.dto.BasketReadDto;
import ru.nvkz.service.BasketProductService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RequiredArgsConstructor
@WithMockUser(username = "test@mail.ru", password = "123", authorities = {"ADMIN", "USER"})
class BasketProductControllerIT extends IntegrationTestBase {

    public static final long BASKET_ID_1 = 1L;
    private final MockMvc mockMvc;
    private final BasketProductService basketProductService;

    @Test
    void create() throws Exception {
        mockMvc.perform(post("/basket-products")
                        .param(BasketProductCreateEditDto.Fields.productId, "2")
                        .param(BasketProductCreateEditDto.Fields.basketId, "1")
                        .sessionAttr("basket", new BasketReadDto(BASKET_ID_1, null, null, null))
                )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrlPattern("/products/{\\d+}")
                );
        assertThat(basketProductService.findAllByBasketId(BASKET_ID_1)).hasSize(3);
    }

    @Test
    void createIsForbidden() throws Exception {
        mockMvc.perform(post("/basket-products")
                        .param(BasketProductCreateEditDto.Fields.productId, "2")
                        .param(BasketProductCreateEditDto.Fields.basketId, "2")
                        .sessionAttr("basket", new BasketReadDto(BASKET_ID_1, null, null, null))
                )
                .andExpectAll(
                        status().isForbidden()
                );
    }

    @Test
    void update() throws Exception {
        mockMvc.perform(post("/basket-products/1/update")
                        .param(BasketProductCreateEditDto.Fields.productId, "1")
                        .param(BasketProductCreateEditDto.Fields.basketId, "1")
                        .param(BasketProductCreateEditDto.Fields.sum, "12998")
                        .param(BasketProductCreateEditDto.Fields.count, "3")
                        .param(BasketProductCreateEditDto.Fields.isSelected, "true")
                        .sessionAttr("basket", new BasketReadDto(BASKET_ID_1, null, null, null))
                )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrlPattern("/baskets/{\\d+}")
                );
    }

    @Test
    void updateIsNotFound() throws Exception {
        mockMvc.perform(post("/basket-products/-111/update")
                        .sessionAttr("basket", new BasketReadDto(-111L, null, null, null))
                )
                .andExpectAll(
                        status().isNotFound()
                );
    }

    @Test
    void updateIsForbidden() throws Exception {
        mockMvc.perform(post("/basket-products/2/update")
                        .sessionAttr("basket", new BasketReadDto(BASKET_ID_1, null, null, null))
                )
                .andExpectAll(
                        status().isForbidden()
                );
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(post("/basket-products/1/delete")
                        .param("basketId", "1")
                        .sessionAttr("basket", new BasketReadDto(BASKET_ID_1, null, null, null))
                )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrlPattern("/baskets/{\\d+}")
                );
        assertThat(basketProductService.findAllByBasketId(BASKET_ID_1)).hasSize(1);
    }

    @Test
    void deleteIsNotFound() throws Exception {
        mockMvc.perform(post("/basket-products/-111/delete")
                        .param("basketId", "1")
                        .sessionAttr("basket", new BasketReadDto(-111L, null, null, null))
                )
                .andExpectAll(
                        status().isNotFound()
                );
    }

    @Test
    void deleteIsForbidden() throws Exception {
        mockMvc.perform(post("/basket-products/2/delete")
                        .param("basketId", "1")
                        .sessionAttr("basket", new BasketReadDto(BASKET_ID_1, null, null, null))
                )
                .andExpectAll(
                        status().isForbidden()
                );
    }
}