package ru.nvkz.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.nvkz.dto.OrderReadDto;
import ru.nvkz.entity.Order;

@Component
@RequiredArgsConstructor
public class OrderReadMapper implements Mapper<Order, OrderReadDto> {

    private final UserReadMapper userReadMapper;
    private final BasketReadMapper basketReadMapper;

    @Override
    public OrderReadDto map(Order object) {
        return new OrderReadDto(
                object.getId(),
                getDto(object.getUser(), userReadMapper),
                object.getCreatedAt(),
                object.getUpdatedAt(),
                object.getSum(),
                object.getOrderStatus(),
                object.getCount(),
                getDto(object.getBasket(), basketReadMapper)
        );
    }
}
