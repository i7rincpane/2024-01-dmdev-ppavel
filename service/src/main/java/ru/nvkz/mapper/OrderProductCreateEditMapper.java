package ru.nvkz.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.nvkz.dto.OrderProductCreateEditDto;
import ru.nvkz.entity.OrderProduct;
import ru.nvkz.repository.OrderRepository;
import ru.nvkz.repository.ProductRepository;

@Component
@RequiredArgsConstructor
public class OrderProductCreateEditMapper implements Mapper<OrderProductCreateEditDto, OrderProduct> {

    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    @Override
    public OrderProduct map(OrderProductCreateEditDto object) {
        OrderProduct orderProduct = new OrderProduct();
        copy(object, orderProduct);
        return orderProduct;
    }

    @Override
    public OrderProduct map(OrderProductCreateEditDto fromObject, OrderProduct toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    private void copy(OrderProductCreateEditDto objectDto, OrderProduct toObject) {
        toObject.setOrder(getEntity(objectDto.getOrderId(), orderRepository));
        toObject.setProduct(getEntity(objectDto.getProductId(), productRepository));
        toObject.setCount(objectDto.getCount());
        toObject.setSum(objectDto.getSum());
    }
}
