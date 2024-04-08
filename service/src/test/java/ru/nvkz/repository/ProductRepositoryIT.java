package ru.nvkz.repository;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.nvkz.IntegrationTestBase;
import ru.nvkz.entity.Product;
import ru.nvkz.filter.ProductFilter;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

// TODO: протестировать фильтр, после реализации функционала по добавлению и удалению фильтра

@RequiredArgsConstructor
class ProductRepositoryIT extends IntegrationTestBase {

    private final ProductRepository repository;

    @Test
    void findAllDistinctByProductFilterAllParams() {
        String[] productNamesExpected = {"Электрическая варочная поверхность DEXP 4M2CTYL/B", "Электрическая варочная поверхность DEXP EH-C2NSMA/B"};
        ProductFilter productFilter = ProductFilter.builder()
                //.propertyIdBatch(List.of(1L, 9L))
                .priceFrom(new BigDecimal(2000.0))
                .priceBy(new BigDecimal(9999.0))
                .build();

        List<Product> productActualBatch = repository.findAllDistinctByProductFilter(productFilter, 5);

        Assertions.assertThat(productActualBatch).hasSize(2);
        List<String> productNameActualBatch = productActualBatch.stream().map(Product::getName).toList();
        assertThat(productNameActualBatch).contains(productNamesExpected);
    }

    @Test
    @DisplayName("поиск с учетом всех типов параметров")
    void testFilter1() {

    }

    @Test
    @DisplayName("поиск по основным параметрам вместе и отдельно")
    void testFilter2() {

    }

    @Test
    @DisplayName("поиск текстовому фильтру для нескольких однотипных свойств")
    void testFilter3() {

    }


    @Test
    @DisplayName("поиск числовому фильтру, от, до, между, для нескольких однотипных свойств")
    void testFilter4() {

    }

    @Test
    @DisplayName("поиск фильтру вещественных чисел, от, до, между, для нескольких однотипных свойств")
    void testFilter5() {

    }

}