package ru.nvkz.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nvkz.dto.ProductPropertyCreateEditDto;
import ru.nvkz.dto.ProductPropertyReadDto;
import ru.nvkz.dto.PropertyReadDto;
import ru.nvkz.mapper.ProductPropertyCreateEditMapper;
import ru.nvkz.mapper.ProductPropertyReadMapper;
import ru.nvkz.repository.ProductPropertyRepository;
import ru.nvkz.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductPropertyService {
    private final ProductPropertyRepository productPropertyRepository;
    private final ProductPropertyReadMapper productPropertyReadMapper;
    private final PropertyService propertyService;
    private final ProductPropertyCreateEditMapper productPropertyCreateEditMapper;


    public Optional<ProductPropertyReadDto> findById(Long id) {
        return productPropertyRepository.findById(id)
                .map(productPropertyReadMapper::map);
    }

    @Transactional
    public ProductPropertyReadDto create(ProductPropertyCreateEditDto productPropertyDto) {
        return Optional.of(productPropertyDto)
                .map(productPropertyCreateEditMapper::map)
                .map(productPropertyRepository::save)
                .map(productPropertyReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<ProductPropertyReadDto> update(Long id, ProductPropertyCreateEditDto productPropertyValueDto) {
        return productPropertyRepository.findById(id)
                .map(entity -> productPropertyCreateEditMapper.map(productPropertyValueDto, entity))
                .map(productPropertyRepository::saveAndFlush)
                .map(productPropertyReadMapper::map);
    }

    @Transactional
    public boolean delete(Long id) {
        return productPropertyRepository.findById(id).map(
                        entity -> {
                            productPropertyRepository.delete(entity);
                            productPropertyRepository.flush();
                            return true;
                        })
                .orElse(false);
    }

    @Transactional
    public List<ProductPropertyReadDto> findByProductIdAndCreateNewProperties(Long productId) {
        List<ProductPropertyReadDto> productPropertyValues = productPropertyRepository.findByProductId(productId).stream().map(productPropertyReadMapper::map).collect(Collectors.toList());
        checkAndCreateNewProductProperty(productPropertyValues);
        return productPropertyValues;
    }

    private void checkAndCreateNewProductProperty(List<ProductPropertyReadDto> productPropertyValues) {
        productPropertyValues.stream().findFirst().ifPresent(
                (productProperty) -> {
                    CollectionUtils.merge(productPropertyValues, propertyService.findByCategoryId(productProperty.getProduct().getCategory().getId()),
                            this::isEqualsPropertyId,
                            (newProperty) -> this.create(ProductPropertyCreateEditDto.builder()
                                    .propertyId(newProperty.getId())
                                    .productId(productProperty.getId())
                                    .build())
                    );
                });
    }

    private boolean isEqualsPropertyId(PropertyReadDto property, ProductPropertyReadDto productPropertyValue) {
        return property.getId().equals(productPropertyValue.getProperty().getId());
    }

}
