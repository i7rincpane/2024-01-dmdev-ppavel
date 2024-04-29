package ru.nvkz.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import ru.nvkz.IntegrationTestBase;
import ru.nvkz.dto.OrderReadDto;
import ru.nvkz.entity.OrderStatus;
import ru.nvkz.exeption.ValidationException;
import ru.nvkz.validation.validator.Error;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;


@RequiredArgsConstructor
class OrderServiceIT extends IntegrationTestBase {

    private final static Long BASKET_ID_2 = 2l;
    private final static Long BASKET_ID_WITH_NOT_AVAILABLE_PRODUCT = 1l;
    private final static Long BASKET_ID_WITH_BASKET_EMPTY = 3l;
    private final static Long BASKET_ID_WITHOUT_SELECTED_PRODUCTS = 4l;

    private final OrderService orderService;
    private final OrderProductService orderProductService;
    private final BasketProductService basketProductService;

    @Test
    void create() {

        OrderReadDto order = orderService.create(BASKET_ID_2);
                assertThat(order.getId()).isNotNull();

        assertEquals(2, order.getCount());
        assertThat(order.getSum()).isEqualByComparingTo(new BigDecimal(7849));
        assertSame(OrderStatus.PROCESSING, order.getOrderStatus());
        assertEquals(2L, order.getBasket().getId());
        assertEquals(1L, order.getUser().getId());

        assertThat( orderProductService.findAllByOrderId(order.getId())).hasSize(2);
        assertThat( basketProductService.findAllByBasketId(BASKET_ID_2)).hasSize(1);

    }

    @Test
    void throwValidationExceptionIfBasketProductIsEmpty() {
        assertAll(
                () -> {
                    var exception = assertThrows(ValidationException.class, () -> orderService.create(BASKET_ID_WITH_NOT_AVAILABLE_PRODUCT));
                    assertThat(exception.getErrors()).hasSize(1);
                    assertThat(exception.getErrors().get(0)).isEqualTo(Error.of(102, "", Map.of(2L, String.format("Отсуствует в указанном количестве (%d). Доступно: %d", 1, 0))));
                }
        );
    }

    @Test
    void throwValidationExceptionIfNotSelectedBasketProduct() {
        assertAll(
                () -> {
                    var exception = assertThrows(ValidationException.class, () -> orderService.create(BASKET_ID_WITHOUT_SELECTED_PRODUCTS));
                    assertThat(exception.getErrors()).hasSize(1);
                    assertThat(exception.getErrors().get(0)).isEqualTo(Error.of(101, "Продукт не выбран", Collections.emptyMap()));
                }
        );
    }

    @Test
    void throwValidationExceptionIfMissingRequiredBasketProduct() {
        assertAll(
                () -> {
                    var exception = assertThrows(ValidationException.class, () -> orderService.create(BASKET_ID_WITH_BASKET_EMPTY));
                    assertThat(exception.getErrors()).hasSize(1);
                    assertThat(exception.getErrors().get(0)).isEqualTo(Error.of(100, "Корзина пуста", Collections.emptyMap()));
                }
        );
    }

}