package ru.nvkz.listener;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.nvkz.entity.Basket;
import ru.nvkz.entity.BasketProduct;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Component
public class BasketProductListener {

    @PostPersist
    public void postPersist(BasketProduct basketProduct) {
        updateBasketCountAndSumm(basketProduct);
    }

    @PostRemove
    public void postRemove(BasketProduct basketProduct) {
        updateBasketCountAndSumm(basketProduct);
    }

    @PostUpdate
    public void postUpdate(BasketProduct basketProduct) {
        updateBasketCountAndSumm(basketProduct);
    }

    private BasketProduct updateBasketCountAndSumm(BasketProduct basketProduct) {
        Basket basket = basketProduct.getBasket();
        List<BasketProduct> basketProductsIsSelected = basket.getBasketProducts().stream().filter(BasketProduct::getIsSelected).toList();
        basket.setCount(basketProductsIsSelected.stream().map(BasketProduct::getCount).mapToInt(Integer::intValue).sum());
        basket.setSum(basketProductsIsSelected.stream().map(BasketProduct::getSum).reduce(BigDecimal.ZERO, BigDecimal::add));
        return basketProduct;
    }
}
