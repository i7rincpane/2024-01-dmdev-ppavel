package ru.nvkz.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nvkz.dto.OrderProductCreateEditDto;
import ru.nvkz.dto.OrderProductReadDto;
import ru.nvkz.entity.OrderProduct;
import ru.nvkz.entity.Product;
import ru.nvkz.mapper.OrderProductCreateEditMapper;
import ru.nvkz.mapper.OrderProductReadMapper;
import ru.nvkz.repository.OrderProductRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderProductService {

    private final OrderProductRepository orderProductRepository;
    private final OrderProductReadMapper orderProductReadMapper;
    private final OrderProductCreateEditMapper orderProductCreateEditMapper;

    @Transactional
    public OrderProductReadDto create(OrderProductCreateEditDto orderProduct) {
        return Optional.of(orderProduct)
                .map(orderProductCreateEditMapper::map)
                .map(orderProductRepository::save)
                .map(this::updateProductCount)
                .map(orderProductReadMapper::map)
                .orElseThrow();
    }

    public List<OrderProductReadDto> findAllByOrderId(Long orderId) {
        return orderProductRepository.findByOrderId(orderId).stream()
                .map(orderProductReadMapper::map)
                .toList();
    }

    private OrderProduct updateProductCount(OrderProduct orderProduct) {
        Product product = orderProduct.getProduct();
        product.setCount(product.getCount() - orderProduct.getCount());
        return orderProduct;
    }

}
