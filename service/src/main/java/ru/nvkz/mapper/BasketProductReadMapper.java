package ru.nvkz.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.nvkz.dto.BasketProductReadDto;
import ru.nvkz.entity.BasketProduct;

@Component
@RequiredArgsConstructor
public class BasketProductReadMapper implements Mapper<BasketProduct, BasketProductReadDto> {

    private final BasketReadMapper basketReadMapper;
    private final ProductReadMapper productReadMapper;

    @Override
    public BasketProductReadDto map(BasketProduct object) {
        return new BasketProductReadDto(
                object.getId(),
                getDto(object.getProduct(), productReadMapper),
                getDto(object.getBasket(), basketReadMapper),
                object.getCount(),
                object.getSum(),
                object.getIsActive()
        );
    }
}
