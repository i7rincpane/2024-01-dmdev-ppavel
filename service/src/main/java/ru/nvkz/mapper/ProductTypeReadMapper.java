package ru.nvkz.mapper;

import org.springframework.stereotype.Component;
import ru.nvkz.dto.ProductTypeReadDto;
import ru.nvkz.entity.ProductType;

import java.util.Optional;

@Component
public class ProductTypeReadMapper implements Mapper<ProductType, ProductTypeReadDto> {

    @Override
    public ProductTypeReadDto map(ProductType object) {
        return ProductTypeReadDto.builder()
                .id(object.getId())
                .name(object.getName())
                .parentDto(getParentDro(object.getParent()))
                .build();
    }

    private ProductTypeReadDto getParentDro(ProductType productType) {
        return Optional.ofNullable(productType).map(this::mapWithoutParent).orElse(null);
    }

    private ProductTypeReadDto mapWithoutParent(ProductType object) {
        return ProductTypeReadDto.builder()
                .id(object.getId())
                .name(object.getName())
                .build();
    }
}
