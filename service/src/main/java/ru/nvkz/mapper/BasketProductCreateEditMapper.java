package ru.nvkz.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.nvkz.dto.BasketProductCreateEditDto;
import ru.nvkz.entity.BasketProduct;
import ru.nvkz.repository.BasketRepository;
import ru.nvkz.repository.ProductRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BasketProductCreateEditMapper implements Mapper<BasketProductCreateEditDto, BasketProduct> {

    private final BasketRepository basketRepository;

    private final ProductRepository productRepository;

    @Override
    public BasketProduct map(BasketProductCreateEditDto object) {
        BasketProduct productBasket = new BasketProduct();
        copy(object, productBasket);
        return productBasket;
    }

    @Override
    public BasketProduct map(BasketProductCreateEditDto fromObject, BasketProduct toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    private void copy(BasketProductCreateEditDto objectDto, BasketProduct toObject) {
        toObject.setBasket(getEntity(objectDto.getBasketId(), basketRepository));
        toObject.setProduct(getEntity(objectDto.getProductId(), productRepository));
        toObject.setCount(objectDto.getCount());
        toObject.setIsSelected(Optional.ofNullable(objectDto.getIsSelected()).orElse(false));
    }
}
