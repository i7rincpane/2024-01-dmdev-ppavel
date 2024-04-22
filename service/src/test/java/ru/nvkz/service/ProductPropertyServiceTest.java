package ru.nvkz.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.nvkz.dto.CategoryReadDto;
import ru.nvkz.dto.ProductPropertyReadDto;
import ru.nvkz.dto.ProductReadDto;
import ru.nvkz.dto.PropertyReadDto;
import ru.nvkz.entity.Product;
import ru.nvkz.entity.ProductProperty;
import ru.nvkz.entity.Property;
import ru.nvkz.mapper.ProductPropertyReadMapper;
import ru.nvkz.repository.ProductPropertyRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
class ProductPropertyServiceTest {

    @Test
    void checkAndAddNewProperty() {

        PropertyService propertyService = Mockito.mock();
        Mockito.doReturn(Arrays.asList(
                        PropertyReadDto.builder().id(1L).build(),
                        PropertyReadDto.builder().id(2L).build(),
                        PropertyReadDto.builder().id(3L).build()))
                .when(propertyService).findByCategoryId(Mockito.any());
        ProductPropertyRepository productPropertyRepository = Mockito.mock();

        var productPropertyValue1 = getProductPropertyValue(1L);
        var productPropertyValue2 = getProductPropertyValue(2L);

        List<ProductProperty> productProperties = new ArrayList<>();
        productProperties.add(productPropertyValue1);
        productProperties.add(productPropertyValue2);

        Mockito.doReturn(productProperties)
                .when(productPropertyRepository).findByProductId(Mockito.any());

        ProductPropertyReadMapper productPropertyReadMapper = Mockito.mock();

        Mockito.doReturn(getProductPropertyValueDto(1L))
                .when(productPropertyReadMapper).map(productPropertyValue1);

        Mockito.doReturn(getProductPropertyValueDto(2L))
                .when(productPropertyReadMapper).map(productPropertyValue2);

        ProductPropertyService service = new ProductPropertyService(
                productPropertyRepository,
                productPropertyReadMapper,
                //           propertyService,
                null);

        var values = service.findByProductId(1L);
        System.out.println();
        System.out.println(values.size());
        System.out.println();
        values.forEach(System.out::println);
    }

    private ProductPropertyReadDto getProductPropertyValueDto(Long propertyId) {
        return ProductPropertyReadDto.builder()
                .product(ProductReadDto.builder()
                        .category(CategoryReadDto.builder().build())
                        .build())
                .property(
                        PropertyReadDto.builder()
                                .id(propertyId)
                                .build()
                )
                .build();
    }

    private ProductProperty getProductPropertyValue(Long propertyId) {
        return ProductProperty.builder()
                .product(Product.builder().build())
                .property(
                        Property.builder()
                                .id(propertyId)
                                .build()
                )
                .build();
    }

}