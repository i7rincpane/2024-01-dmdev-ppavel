package ru.nvkz.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.nvkz.dto.ProductPropertyReadDto;
import ru.nvkz.dto.ProductReadDto;
import ru.nvkz.dto.PropertyReadDto;
import ru.nvkz.dto.StringClassifierReadDto;
import ru.nvkz.entity.Product;
import ru.nvkz.entity.ProductProperty;
import ru.nvkz.entity.Property;
import ru.nvkz.entity.StringClassifier;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductPropertyReadMapper implements Mapper<ProductProperty, ProductPropertyReadDto> {

    private final PropertyReadMapper propertyReadMapper;
    private final ProductReadMapper productReadMapper;
    private final StringClassifierReadMapper stringClassifierReadMapper;

    @Override
    public ProductPropertyReadDto map(ProductProperty object) {
        return ProductPropertyReadDto.builder()
                .id(object.getId())
                .product(getProduct(object.getProduct()))
                .property(getPropertyDto(object.getProperty()))
                .numberValue(object.getNumberValue())
                .dateValue(object.getDateValue())
                .booleanValue(object.getBooleanValue())
                .floatValue(object.getFloatValue())
                .textValue(object.getTextValue())
                .stringClassifier(getStringClassifier(object.getStringClassifier()))
                .build();
    }

    private ProductReadDto getProduct(Product product) {
        return Optional.ofNullable(product).map(productReadMapper::map).orElse(null);
    }

    private StringClassifierReadDto getStringClassifier(StringClassifier stringClassifier) {
        return Optional.ofNullable(stringClassifier).map(stringClassifierReadMapper::map).orElse(null);
    }


    private PropertyReadDto getPropertyDto(Property property) {
        return Optional.ofNullable(property).map(propertyReadMapper::map).orElse(null);
    }

}
