package ru.nvkz.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nvkz.dto.BasketProductCreateEditDto;
import ru.nvkz.dto.BasketProductReadDto;
import ru.nvkz.entity.Basket;
import ru.nvkz.entity.BasketProduct;
import ru.nvkz.mapper.BasketProductCreateEditMapper;
import ru.nvkz.mapper.BasketProductReadMapper;
import ru.nvkz.repository.BasketProductRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BasketProductService {

    private final BasketProductRepository basketProductRepository;
    private final BasketProductReadMapper basketProductReadMapper;
    private final BasketProductCreateEditMapper basketProductCreateEditMapper;

    public List<BasketProductReadDto> findAll() {
        return basketProductRepository.findAll().stream()
                .map(basketProductReadMapper::map)
                .toList();
    }

    public Optional<BasketProductReadDto> findById(Long id) {
        return basketProductRepository.findById(id)
                .map(basketProductReadMapper::map);
    }

    @Transactional
    public BasketProductReadDto create(BasketProductCreateEditDto basketProductCreateEditDto) {
        return Optional.of(basketProductCreateEditDto)
                .map(basketProductCreateEditMapper::map)
                .map(it -> {
                    it.setCount(1);
                    it.setIsActive(true);
                    it.setSum(new BigDecimal(0));
                    return it;
                })
                .map(basketProductRepository::save)
                .map(this::updateBasketCountAndSumm)
                .map(basketProductReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<BasketProductReadDto> update(Long id, BasketProductCreateEditDto basketProductCreateEditDto) {
        return basketProductRepository.findById(id)
                .map(entity -> basketProductCreateEditMapper.map(basketProductCreateEditDto, entity))
                .map(basketProductRepository::saveAndFlush)
                .map(this::updateBasketCountAndSumm)
                .map(basketProductReadMapper::map);
    }

    @Transactional
    public boolean delete(Long id) {
        return basketProductRepository.findById(id).map(
                        entity -> {
                            basketProductRepository.delete(entity);
                            basketProductRepository.flush();
                            updateBasketCountAndSumm(entity);
                            return true;
                        })
                .orElse(false);
    }

    public List<BasketProductReadDto> findAllByBasketId(Long basketId) {
        return basketProductRepository.findAllByBasketId(basketId).stream()
                .map((v) -> basketProductReadMapper.map(v))
                .toList();
    }

    private BasketProduct updateBasketCountAndSumm(BasketProduct basketProduct) {
        Basket basket = basketProduct.getBasket();
        List<BasketProductReadDto> basketProductsIsActive = this.findAllByBasketId(basket.getId()).stream().filter(BasketProductReadDto::getIsActive).toList();
        basket.setCount(basketProductsIsActive.stream().map(BasketProductReadDto::getCount).mapToInt(Integer::intValue).sum());
        basket.setSum(basketProductsIsActive.stream().map(BasketProductReadDto::getSum).reduce(BigDecimal.ZERO, BigDecimal::add));
        return basketProduct;
    }
}
