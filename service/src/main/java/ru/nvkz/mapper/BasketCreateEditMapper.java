package ru.nvkz.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.nvkz.dto.BasketCreateEditDto;
import ru.nvkz.entity.Basket;
import ru.nvkz.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class BasketCreateEditMapper implements Mapper<BasketCreateEditDto, Basket> {

    private final UserRepository userRepository;

    @Override
    public Basket map(BasketCreateEditDto object) {
        Basket basket = new Basket();
        copy(object, basket);
        return basket;
    }

    @Override
    public Basket map(BasketCreateEditDto fromObject, Basket toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    private void copy(BasketCreateEditDto objectDto, Basket toObject) {
        toObject.setUser(getEntity(objectDto.getUserId(), userRepository));
        toObject.setCount(objectDto.getCount());
        toObject.setSum(objectDto.getSum());
    }
}
