package ru.nvkz.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.nvkz.dto.ProductPropertyCreateEditDto;
import ru.nvkz.entity.Product;
import ru.nvkz.entity.ProductProperty;
import ru.nvkz.entity.Property;
import ru.nvkz.entity.StringClassifier;
import ru.nvkz.repository.ProductRepository;
import ru.nvkz.repository.PropertyRepository;
import ru.nvkz.repository.StringClassifierRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductPropertyCreateEditMapper implements Mapper<ProductPropertyCreateEditDto, ProductProperty> {

    private final PropertyRepository propertyRepository;

    private final ProductRepository productRepository;

    private final StringClassifierRepository stringClassifierRepository;

    @Override
    public ProductProperty map(ProductPropertyCreateEditDto object) {
        ProductProperty productProperty = new ProductProperty();
        copy(object, productProperty);
        return productProperty;
    }

    @Override
    public ProductProperty map(ProductPropertyCreateEditDto fromObject, ProductProperty toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    private void copy(ProductPropertyCreateEditDto objectDto, ProductProperty toObject) {
        toObject.setProperty(getProperty(objectDto.getPropertyId()));
        toObject.setProduct(getProduct(objectDto.getProductId()));
        toObject.setDateValue(objectDto.getDateValue());
        toObject.setBooleanValue(objectDto.getBooleanValue());
        toObject.setFloatValue(objectDto.getFloatValue());
        toObject.setTextValue(objectDto.getTextValue());
        toObject.setNumberValue(objectDto.getNumberValue());
        toObject.setStringClassifier(getStringClassifier(objectDto.getStringClassifierId()));
    }

    private StringClassifier getStringClassifier(Long stringClassifierId) {
        return Optional.ofNullable(stringClassifierId).flatMap(stringClassifierRepository::findById).orElse(null);
    }

    private Property getProperty(Long propertyId) {
        return Optional.ofNullable(propertyId).flatMap(propertyRepository::findById).orElse(null);
    }

    private Product getProduct(Long productId) {
        return Optional.ofNullable(productId).flatMap(productRepository::findById).orElse(null);
    }

}
