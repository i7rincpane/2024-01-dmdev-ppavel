package ru.nvkz.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import ru.nvkz.IntegrationTestBase;
import ru.nvkz.dto.BasketProductCreateEditDto;
import ru.nvkz.dto.BasketProductReadDto;
import ru.nvkz.entity.BasketProduct;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RequiredArgsConstructor
class BasketProductServiceIT extends IntegrationTestBase {

    public static final long BASKET_ID_1 = 1L;
    public static final long BASKET_PRODUCT_ID_1 = 1L;
    public static final long PRODUCT_ID_1 = 1L;
    public static final long PRODUCT_ID_2 = 2L;
    private final BasketProductService basketProductService;
    private final BasketService basketService;

    @Test
    void createOrCountUpdate() {
        BasketProductCreateEditDto basketProductCreateEditDto = new BasketProductCreateEditDto(
                PRODUCT_ID_2,
                BASKET_ID_1,
                1,
                true
        );

        BasketProductReadDto actualResult = basketProductService.createOrCountUpdate(basketProductCreateEditDto);

        assertThat(actualResult.getId()).isNotNull();
        assertEquals(1, actualResult.getCount());
        assertThat(new BigDecimal(6999)).isEqualByComparingTo(actualResult.getSum());
        assertEquals(true, actualResult.getIsSelected());
        assertThat(new BigDecimal(25552)).isEqualByComparingTo(actualResult.getBasket().getSum());
        assertEquals(4, actualResult.getBasket().getCount());

        BasketProductReadDto actualResult2 = basketProductService.createOrCountUpdate(basketProductCreateEditDto);
        assertEquals(2, actualResult2.getCount());
    }

    @Test
    void update() {
        BasketProductCreateEditDto basketProductCreateEditDto = new BasketProductCreateEditDto(
                PRODUCT_ID_1,
                BASKET_ID_1,
                3,
                true
        );

        Optional<BasketProductReadDto> actualResult = basketProductService.update(BASKET_PRODUCT_ID_1, basketProductCreateEditDto);
        assertTrue(actualResult.isPresent());
        actualResult.ifPresent(basketProduct -> {
            assertEquals(3, basketProduct.getCount());
            assertThat(basketProduct.getSum()).isEqualByComparingTo(new BigDecimal(19497));
            assertThat(basketProduct.getBasket().getSum()).isEqualByComparingTo(new BigDecimal(25052));
            assertEquals(4, basketProduct.getBasket().getCount());
        });
    }


    @Test
    void exclude() {
        Boolean UNSELECTED = false;
        BasketProductCreateEditDto basketProductCreateEditDto = new BasketProductCreateEditDto(
                PRODUCT_ID_1,
                BASKET_ID_1,
                3,
                UNSELECTED
        );

        Optional<BasketProductReadDto> actualResult = basketProductService.update(BASKET_PRODUCT_ID_1, basketProductCreateEditDto);
        assertTrue(actualResult.isPresent());
        actualResult.ifPresent(basketProduct -> {
            assertEquals(3, basketProduct.getCount());
            assertThat(basketProduct.getSum()).isEqualByComparingTo(new BigDecimal(19497));
            assertThat(basketProduct.getBasket().getSum()).isEqualByComparingTo(new BigDecimal(5555));
            assertEquals(1, basketProduct.getBasket().getCount());
        });
    }

    @Test
    void delete() {
        assertFalse(basketProductService.delete(-124L));
        assertTrue(basketProductService.delete(BASKET_PRODUCT_ID_1));
        basketService.findById(BASKET_ID_1).ifPresent(basket -> {
            assertThat(basket.getSum()).isEqualByComparingTo(new BigDecimal(5555));
            assertEquals(1, basket.getCount());
        });
    }

    @Test
    void findAllByBasketId() {
        List<BasketProductReadDto> result = basketProductService.findAllByBasketId(BASKET_ID_1);
        assertThat(result).hasSize(2);
    }

    @Test
    void findAllAvailableByBasketId() {
        List<BasketProduct> result = basketProductService.findAllAvailableByBasketId(BASKET_ID_1);
        assertThat(result).hasSize(1);
    }
}