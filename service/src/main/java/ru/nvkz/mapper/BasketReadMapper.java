package ru.nvkz.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.nvkz.dto.BasketReadDto;
import ru.nvkz.entity.Basket;

@Component
@RequiredArgsConstructor
public class BasketReadMapper implements Mapper<Basket, BasketReadDto> {

    private final UserReadMapper userReadMapper;

    @Override
    public BasketReadDto map(Basket object) {
        return new BasketReadDto(
                object.getId(),
                getDto(object.getUser(), userReadMapper),
                object.getSum(),
                object.getCount()
        );
    }
}
