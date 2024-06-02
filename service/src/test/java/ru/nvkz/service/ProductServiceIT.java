package ru.nvkz.service;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ru.nvkz.IntegrationTestBase;
import ru.nvkz.dto.BasketProductCreateEditDto;
import ru.nvkz.dto.BasketProductReadDto;
import ru.nvkz.dto.ProductCreateEditDto;
import ru.nvkz.dto.ProductReadDto;
import ru.nvkz.entity.Product;
import ru.nvkz.filter.ProductFilter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
class ProductServiceIT extends IntegrationTestBase {

    private final ProductService productService;

    @Test
    void findById() {
    }

    @Test
    @DisplayName("поиск по числовым фильтрам между")
    void findAllDistinctByBetweenNumberProperties() {
        String[] productNamesExpected = {"Электрическая варочная поверхность DEXP 4M2CTYL/B", "Электрическая варочная поверхность DEXP EH-C2NSMA/B", "Электрическая варочная поверхность DARINA 1B4TODB", "Электрическая варочная поверхность Weissgauff HV 312 BA"};
        ProductFilter productFilter = ProductFilter.builder()
                .build();

        productFilter.getPropertyIdNumberValueFrom().put(1, 2);
        productFilter.getPropertyIdNumberValueBy().put(1, 4);
        productFilter.getPropertyIdNumberValueFrom().put(7, 28);
        productFilter.getPropertyIdNumberValueBy().put(7, 30);

        Page<ProductReadDto> productActualBatch = productService.findAllDistinctByProductFilter(productFilter, 5l, PageRequest.of(0, 4));

        Assertions.assertThat(productActualBatch).hasSize(4);
        Assertions.assertThat(productActualBatch.getTotalElements()).isEqualTo(4);
        List<String> productNameActualBatch = productActualBatch.map(ProductReadDto::getName).toList();
        assertThat(productNameActualBatch).contains(productNamesExpected);
    }

    @Test
    @DisplayName("поиск по числовым фильтрам до")
    void findAllDistinctByNumberByProperties() {
        String[] productNamesExpected = {"Электрическая варочная поверхность Weissgauff HV 312 BA"};
        ProductFilter productFilter = ProductFilter.builder()
                .build();

        productFilter.getPropertyIdNumberValueFrom().put(1, null);
        productFilter.getPropertyIdNumberValueBy().put(1, 4);
        productFilter.getPropertyIdNumberValueFrom().put(7, null);
        productFilter.getPropertyIdNumberValueBy().put(7, 30);

        Page<ProductReadDto> productActualBatch = productService.findAllDistinctByProductFilter(productFilter, 5l, PageRequest.of(0, 3));

        Assertions.assertThat(productActualBatch).hasSize(1);
        Assertions.assertThat(productActualBatch.getTotalElements()).isEqualTo(1);
        List<String> productNameActualBatch = productActualBatch.map(ProductReadDto::getName).toList();

        assertThat(productNameActualBatch).contains(productNamesExpected);
    }

    @Test
    @DisplayName("поиск по числовым фильтрам от" +
            "")
    void findAllDistinctByNumberFromProperties() {
        String[] productNamesExpected = {"Электрическая варочная поверхность DARINA 1B4TODB"};
        ProductFilter productFilter = ProductFilter.builder()
                .build();

        productFilter.getPropertyIdNumberValueFrom().put(1, 2);
        productFilter.getPropertyIdNumberValueBy().put(1, null);
        productFilter.getPropertyIdNumberValueFrom().put(7, 28);
        productFilter.getPropertyIdNumberValueBy().put(7, null);

        Page<ProductReadDto> productActualBatch = productService.findAllDistinctByProductFilter(productFilter, 5l, PageRequest.of(0, 3));

        Assertions.assertThat(productActualBatch).hasSize(1);
        Assertions.assertThat(productActualBatch.getTotalElements()).isEqualTo(1);
        List<String> productNameActualBatch = productActualBatch.map(ProductReadDto::getName).toList();

        assertThat(productNameActualBatch).contains(productNamesExpected);
    }

    @Test
    @DisplayName("поиск по числовому фильтру от")
    void findAllDistinctByNumberFromProperty() {
        String[] productNamesExpected = {"Электрическая варочная поверхность DARINA 1B4TODB", "Электрическая варочная поверхность Weissgauff HV 312 BA"};
        ProductFilter productFilter = ProductFilter.builder()
                .build();

        productFilter.getPropertyIdNumberValueFrom().put(1, 2);
        productFilter.getPropertyIdNumberValueBy().put(1, null);

        Page<ProductReadDto> productActualBatch = productService.findAllDistinctByProductFilter(productFilter, 5l, PageRequest.of(0, 3));

        Assertions.assertThat(productActualBatch).hasSize(2);
        Assertions.assertThat(productActualBatch.getTotalElements()).isEqualTo(2);
        List<String> productNameActualBatch = productActualBatch.map(ProductReadDto::getName).toList();

        assertThat(productNameActualBatch).contains(productNamesExpected);
    }

    @Test
    void findAllDistinctByProductFilter() {
        String[] productNamesExpected = {"Электрическая варочная поверхность DEXP 4M2CTYL/B", "Электрическая варочная поверхность DEXP EH-C2NSMA/B", "Электрическая варочная поверхность DARINA 1B4TODB"};
        ProductFilter productFilter = ProductFilter.builder()
                //.propertyIdBatch(List.of(1L, 9L))
                .priceFrom(new BigDecimal(2000.0))
                .priceBy(new BigDecimal(9999.0))
                .build();

        Page<ProductReadDto> productActualBatch = productService.findAllDistinctByProductFilter(productFilter, 5l, PageRequest.of(0, 3));

        Assertions.assertThat(productActualBatch).hasSize(3);
        Assertions.assertThat(productActualBatch.getTotalElements()).isEqualTo(12);
        List<String> productNameActualBatch = productActualBatch.map(ProductReadDto::getName).toList();
        assertThat(productNameActualBatch).contains(productNamesExpected);
    }

    @Test
    void update() {
        ProductCreateEditDto productCreateEditDto = ProductCreateEditDto.builder()
                .categoryId(5L)
                .price(new BigDecimal(6999.00))
                .count(10)
                .model("EH-C2NSMA/B")
                .code(1332067)
                .producerId(1L)
                .build();

        Optional<ProductReadDto> actualResult = productService.update(2L, productCreateEditDto);
        assertTrue(actualResult.isPresent());
        actualResult.ifPresent(product -> {
            assertEquals(productCreateEditDto.getCount(), product.getCount());
            assertEquals(productCreateEditDto.getCategoryId(), product.getCategory().getId());
            assertEquals(productCreateEditDto.getPrice(), product.getPrice());
            assertEquals(productCreateEditDto.getModel(), product.getModel());
            assertEquals(productCreateEditDto.getCode(), product.getCode());
            assertEquals(productCreateEditDto.getProducerId(), product.getProducer().getId());
        });
    }

    @Test
    void create() {
    }

    @Test
    void delete() {
    }

    @Test
    void findImage() {
    }
}