package ru.nvkz.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nvkz.dto.OrderCreateEditDto;
import ru.nvkz.dto.OrderProductCreateEditDto;
import ru.nvkz.dto.OrderReadDto;
import ru.nvkz.entity.Basket;
import ru.nvkz.entity.BasketProduct;
import ru.nvkz.entity.OrderStatus;
import ru.nvkz.exeption.ValidationException;
import ru.nvkz.mapper.OrderCreateEditMapper;
import ru.nvkz.mapper.OrderReadMapper;
import ru.nvkz.repository.OrderRepository;
import ru.nvkz.util.CollectionUtils;
import ru.nvkz.validation.validator.Error;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderReadMapper orderReadMapper;
    private final OrderCreateEditMapper orderCreateEditMapper;
    private final BasketProductService basketProductService;
    private final BasketService basketService;
    private final OrderProductService orderProductService;

    @Transactional
    public OrderReadDto create(Long basketId) {
        List<BasketProduct> basketProducts = basketProductService.findAllByBasketId(basketId, (v) -> v);
        this.validate(basketProducts);
        return basketProducts.stream()
                .findFirst()
                .map(BasketProduct::getBasket)
                .map(this::mapToOrderCreateEditDto)
                .map(this::create)
                .map(savedOrder -> {
                    copySelectedBasketProductsToOrder(basketProducts, savedOrder);
                    deleteSelectedBasketProduct(basketProducts);
                    return savedOrder;
                })
                .orElseThrow();
    }

    public List<OrderReadDto> findAllByUserId(Long userId) {
        return orderRepository.findAllByUserId(userId).stream()
                .map(orderReadMapper::map)
                .toList();
    }

    public Optional<OrderReadDto> findByIdAndUserId(Long id, Long userId) {
        return orderRepository.findByIdAndUserId(id, userId)
                .map(orderReadMapper::map);
    }

    @Transactional
    public Optional<OrderReadDto> update(Long id, OrderCreateEditDto orderCreateEditDto) {
        return orderRepository.findById(id)
                .map(entity -> orderCreateEditMapper.map(orderCreateEditDto, entity))
                .map(orderRepository::saveAndFlush)
                .map(orderReadMapper::map);
    }

    private void deleteSelectedBasketProduct(List<BasketProduct> basketProducts) {
        basketProducts.stream()
                .filter(BasketProduct::getIsSelected)
                .map(BasketProduct::getId)
                .forEach(basketProductService::delete);
    }

    private void copySelectedBasketProductsToOrder(List<BasketProduct> basketProducts, OrderReadDto savedOrder) {
        basketProducts.stream()
                .filter(BasketProduct::getIsSelected)
                .map((it) -> new OrderProductCreateEditDto(
                        it.getProduct().getId(),
                        savedOrder.getId(),
                        it.getCount(),
                        it.getSum()
                ))
                .forEach(orderProductService::create);
    }

    private OrderCreateEditDto mapToOrderCreateEditDto(Basket basket) {
        OrderCreateEditDto newOrderCreateEditDto = new OrderCreateEditDto(
                basket.getUser().getId(),
                Instant.now(),
                null,
                basket.getSum(),
                OrderStatus.PROCESSING,
                basket.getCount(),
                basket.getId()
        );
        return newOrderCreateEditDto;
    }

    private void validate(List<BasketProduct> basketProducts) {

        if (basketProducts.isEmpty()) {
            throw new ValidationException(Error.of(100, "Корзина пуста", Collections.emptyMap()));
        }

        List<BasketProduct> selectedBasketProducts = basketProducts.stream()
                .filter(BasketProduct::getIsSelected).toList();

        Map<Long, String> errorDetails = selectedBasketProducts.stream()
                .findFirst()
                .map(BasketProduct::getBasket)
                .map(Basket::getId)
                // TODO::25.04.2024 не получается вынести в отдельный валидатор из за привязки к basketProductService
                .map(basketProductService::findAllAvailableByBasketId)
                .map(availableBasketProducts -> CollectionUtils.except(selectedBasketProducts, availableBasketProducts, (v1, v2) -> v1.getId().equals(v2.getId())))
                .map(notAvailableBasketProducts -> notAvailableBasketProducts.stream()
                        .collect(Collectors.toMap(BasketProduct::getId,
                                (basketProduct) -> String.format("Отсуствует в указанном количестве (%d). Доступно: %d", basketProduct.getCount(), basketProduct.getProduct().getCount()))
                        ))
                .orElseThrow(() -> new ValidationException(Error.of(101, "Продукт не выбран", Collections.emptyMap())));

        if (!errorDetails.isEmpty()) {
            throw new ValidationException(Error.of(102, "", errorDetails));
        }
    }

    private OrderReadDto create(OrderCreateEditDto orderCreateEditDto) {
        return Optional.of(orderCreateEditDto)
                .map(orderCreateEditMapper::map)
                .map(orderRepository::save)
                .map(orderReadMapper::map)
                .orElseThrow();
    }
}
