package ru.nvkz.repository;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nvkz.entity.Product;
import ru.nvkz.filter.ProductFilter;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
class ProductRepositoryIT extends RepositoryBaseIT {

    private final ProductRepository repository;

    @Test
    void findAllDistinctByProductFilterAllParams() {
        String[] productNamesExpected = {"Электрическая варочная поверхность DEXP 4M2CTYL/B", "Электрическая варочная поверхность DEXP EH-C2NSMA/B"};
        ProductFilter productFilter = ProductFilter.builder()
                .productTypeId(5)
                .producerNames(List.of("DEXP"))
                .propertyIdBatch(List.of(1L, 9L))
                .priceFrom(new BigDecimal(2000.0))
                .priceBy(new BigDecimal(9999.0))
                .build();

        List<Product> productActualBatch = repository.findAllDistinctByProductFilter(productFilter);

        Assertions.assertThat(productActualBatch).hasSize(2);
        List<String> productNameActualBatch = productActualBatch.stream().map(Product::getName).toList();
        assertThat(productNameActualBatch).contains(productNamesExpected);
    }

}