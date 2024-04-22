package ru.nvkz.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nvkz.dto.CategoryPathElement;
import ru.nvkz.dto.OrderCreateEditDto;
import ru.nvkz.dto.OrderReadDto;
import ru.nvkz.mapper.OrderCreateEditMapper;
import ru.nvkz.mapper.OrderReadMapper;
import ru.nvkz.repository.OrderRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderReadMapper orderReadMapper;
    private final OrderCreateEditMapper orderCreateEditMapper;

    public List<OrderReadDto> findAll() {
        return orderRepository.findAll().stream()
                .map(orderReadMapper::map)
                .toList();
    }

    public List<OrderReadDto> findAllByUserId(Long userId) {
        return orderRepository.findAllByUserId(userId).stream()
                .map(orderReadMapper::map)
                .toList();
    }

    public Optional<OrderReadDto> findById(Long id) {
        return orderRepository.findById(id)
                .map(orderReadMapper::map);
    }

    @Transactional
    public OrderReadDto create(OrderCreateEditDto orderCreateEditDto) {
        return Optional.of(orderCreateEditDto)
                .map(orderCreateEditMapper::map)
                .map(orderRepository::save)
                .map(orderReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<OrderReadDto> update(Long id, OrderCreateEditDto orderCreateEditDto) {
        return orderRepository.findById(id)
                .map(entity -> orderCreateEditMapper.map(orderCreateEditDto, entity))
                .map(orderRepository::saveAndFlush)
                .map(orderReadMapper::map);
    }

    @Transactional
    public boolean delete(Long id) {
        return orderRepository.findById(id).map(
                        entity -> {
                            orderRepository.delete(entity);
                            orderRepository.flush();
                            return true;
                        })
                .orElse(false);
    }

    public CategoryPathElement create(OrderCreateEditDto order, List<Long> productIds) {
        return null;
    }
}
