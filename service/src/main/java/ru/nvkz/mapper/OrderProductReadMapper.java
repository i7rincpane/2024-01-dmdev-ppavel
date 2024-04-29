package ru.nvkz.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.nvkz.dto.OrderProductReadDto;
import ru.nvkz.entity.OrderProduct;

@Component
@RequiredArgsConstructor
public class OrderProductReadMapper implements Mapper<OrderProduct, OrderProductReadDto> {

    private final OrderReadMapper orderReadMapper;
    private final ProductReadMapper productReadMapper;

    @Override
    public OrderProductReadDto map(OrderProduct object) {
        return new OrderProductReadDto(
                object.getId(),
                getDto(object.getProduct(), productReadMapper),
                getDto(object.getOrder(), orderReadMapper),
                object.getCount(),
                object.getSum()
        );
    }
}
