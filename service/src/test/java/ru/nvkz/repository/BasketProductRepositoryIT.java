package ru.nvkz.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import ru.nvkz.IntegrationTestBase;

@RequiredArgsConstructor
class BasketProductRepositoryIT extends IntegrationTestBase {

    private final BasketProductRepository basketProductRepository;

    @Test
    void findAllByBasketIdAndIsSelectedTrueAndCountLessThanEqualProductCount() {
      var values =  basketProductRepository.findAllByBasketIdAndIsSelectedTrueAndCountLessThanEqualProductCount(1L);
        System.out.println(values);
    }
}