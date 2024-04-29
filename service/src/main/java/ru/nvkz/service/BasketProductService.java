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
import ru.nvkz.mapper.Mapper;
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
                    it.setIsSelected(true);
                    it.setSum(BigDecimal.ZERO);
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
        return this.findAllByBasketId(basketId, basketProductReadMapper);
    }

    public <T> List<T> findAllByBasketId(Long basketId, Mapper<BasketProduct, T> mapper) {
        return basketProductRepository.findAllByBasketId(basketId).stream()
                .map((v) -> mapper.map(v))
                .toList();
    }

    public List<BasketProduct> findAllAvailableByBasketId(Long basketId) {
        return basketProductRepository.findAllByBasketIdAndIsSelectedTrueAndCountLessThanEqualProductCount(basketId);
    }

    private BasketProduct updateBasketCountAndSumm(BasketProduct basketProduct) {
        Basket basket = basketProduct.getBasket();
        List<BasketProductReadDto> basketProductsIsSelected = this.findAllByBasketId(basket.getId()).stream().filter(BasketProductReadDto::getIsSelected).toList();
        basket.setCount(basketProductsIsSelected.stream().map(BasketProductReadDto::getCount).mapToInt(Integer::intValue).sum());
        basket.setSum(basketProductsIsSelected.stream().map(BasketProductReadDto::getSum).reduce(BigDecimal.ZERO, BigDecimal::add));
        return basketProduct;
    }


}
