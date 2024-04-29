package ru.nvkz.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import ru.nvkz.IntegrationTestBase;
import ru.nvkz.dto.BasketCreateEditDto;
import ru.nvkz.dto.BasketReadDto;

import java.math.BigDecimal;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RequiredArgsConstructor
class BasketServiceIT extends IntegrationTestBase {

    private static final Long BASKET_1 = 1L;
    private static final Long USER_2 = 2L;
    private static final Long USER_5_WITHOUT_BASKET = 5L;

    private final BasketService basketService;

    @Test
    void findByUserId() {
        Optional<BasketReadDto> mabyBasket = basketService.findByUserId(USER_2);
        assertTrue(mabyBasket.isPresent());
        mabyBasket.ifPresent(basket -> assertAll(() -> {
            assertEquals(BASKET_1, basket.getId());
            assertEquals(USER_2, basket.getUserReadDto().getId());
            assertThat(new BigDecimal(18553.00)).isEqualByComparingTo(basket.getSum());
            assertEquals(3, basket.getCount());
        }));

    }

    @Test
    void findById() {
        Optional<BasketReadDto> mabyBasket = basketService.findById(BASKET_1);
        assertTrue(mabyBasket.isPresent());
        mabyBasket.ifPresent(basket -> assertAll(() -> {
            assertEquals(BASKET_1, basket.getId());
            assertEquals(2, basket.getUserReadDto().getId());
            assertThat(basket.getSum()).isEqualByComparingTo(new BigDecimal(18553.00));
            assertEquals(3, basket.getCount());
        }));

    }

    @Test
    void create() {

        BasketCreateEditDto basketCreate = new BasketCreateEditDto(
                USER_5_WITHOUT_BASKET,
                BigDecimal.ZERO,
                0

        );

        BasketReadDto actualResult = basketService.create(basketCreate);
        assertThat(actualResult.getId()).isNotNull();
        assertEquals(USER_5_WITHOUT_BASKET, actualResult.getUserReadDto().getId());
        assertThat(actualResult.getSum()).isEqualByComparingTo(new BigDecimal(0.00));
        assertEquals(0, actualResult.getCount());
    }
}