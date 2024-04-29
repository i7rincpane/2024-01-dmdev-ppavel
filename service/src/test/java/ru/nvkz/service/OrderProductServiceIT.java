package ru.nvkz.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import ru.nvkz.IntegrationTestBase;
import ru.nvkz.dto.OrderProductCreateEditDto;
import ru.nvkz.dto.OrderProductReadDto;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RequiredArgsConstructor
class OrderProductServiceIT extends IntegrationTestBase {

    private static final Long PRODUCT_ID_2 = 2L;
    private static final Long ORDER_ID_2 = 2L;
    private final OrderProductService orderProductService;

    @Test
    void createAndUpdateProductCount() {
        OrderProductCreateEditDto orderProductCreateEditDto = new OrderProductCreateEditDto(
                PRODUCT_ID_2,
                ORDER_ID_2,
                1,
                new BigDecimal(6999)
        );

        OrderProductReadDto actualResult = orderProductService.create(orderProductCreateEditDto);

        assertThat(actualResult.getId()).isNotNull();
        assertEquals(1, actualResult.getCount());
        assertThat(actualResult.getSum()).isEqualByComparingTo(new BigDecimal(6999));
        assertEquals(4, actualResult.getProduct().getCount());
    }

    @Test
    void findAllByOrderId() {
        List<OrderProductReadDto> result = orderProductService.findAllByOrderId(ORDER_ID_2);
        assertThat(result).hasSize(1);
    }
}