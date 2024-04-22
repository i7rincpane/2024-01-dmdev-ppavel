package ru.nvkz.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.nvkz.dto.OrderCreateEditDto;
import ru.nvkz.entity.Order;
import ru.nvkz.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class OrderCreateEditMapper implements Mapper<OrderCreateEditDto, Order> {

    private final UserRepository userRepository;

    @Override
    public Order map(OrderCreateEditDto object) {
        Order order = new Order();
        copy(object, order);
        return order;
    }

    @Override
    public Order map(OrderCreateEditDto fromObject, Order toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    private void copy(OrderCreateEditDto objectDto, Order toObject) {
        toObject.setCreatedAt(objectDto.getCreatedAt());
        toObject.setOrderStatus(objectDto.getOrderStatus());
        toObject.setUser(getEntity(objectDto.getUserId(), userRepository));
        toObject.setSum(objectDto.getSum());
        toObject.setUpdatedAt(objectDto.getUpdatedAt());
        toObject.setCount(objectDto.getCount());
    }
}
