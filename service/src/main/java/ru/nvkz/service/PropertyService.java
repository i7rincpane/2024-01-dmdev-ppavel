package ru.nvkz.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nvkz.dto.PropertyCreateEditDto;
import ru.nvkz.dto.PropertyFilterReadDto;
import ru.nvkz.dto.PropertyReadDto;
import ru.nvkz.entity.Product;
import ru.nvkz.entity.Property;
import ru.nvkz.mapper.Mapper;
import ru.nvkz.mapper.PropertyCreateEditMapper;
import ru.nvkz.mapper.PropertyReadMapper;
import ru.nvkz.repository.PropertyRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final PropertyCreateEditMapper propertyCreateEditMapper;
    private final PropertyReadMapper propertyReadMapper;
    private final ProductService productService;

    public List<PropertyReadDto> findByCategoryId(Long categoryId) {
        return this.findByCategoryId(categoryId, propertyReadMapper);
    }

    public List<PropertyReadDto> findMissingPropertiesByProductId(Long productId) {
        Product product = productService.findById(productId, it -> it).orElseThrow();
        List<PropertyReadDto> categoryProperties = this.findByCategoryId(product.getCategory().getId());
        List<PropertyReadDto> productProperties = this.finByProductId(productId);
        categoryProperties.removeAll(productProperties);
        return categoryProperties;
    }

    private List<PropertyReadDto> finByProductId(Long productId) {
        return propertyRepository.findByProductId(productId).stream()
                .map(propertyReadMapper::map)
                .toList();
    }

    public <T> List<T> findByCategoryId(Long categoryId, Mapper<Property, T> mapper) {

        List<Property> result = propertyRepository.findByCategoryId(categoryId).stream()
                .collect(Collectors.toList());

        return result.stream()
                .map(mapper::map)
                .collect(Collectors.toList());
    }

    public List<PropertyFilterReadDto> findAllWithCountProductProperty(Long categoryId) {
        return propertyRepository.findAllWithCountProductProperty(categoryId);
    }

    public Optional<PropertyReadDto> findById(Long id) {
        return propertyRepository.findById(id)
                .map(propertyReadMapper::map);
    }

    @Transactional
    public PropertyReadDto create(PropertyCreateEditDto propertyDto) {
        return Optional.of(propertyDto)
                .map(propertyCreateEditMapper::map)
                .map(propertyRepository::save)
                .map(propertyReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<PropertyReadDto> update(Long id, PropertyCreateEditDto propertyDto) {
        return propertyRepository.findById(id)
                .map(entity -> propertyCreateEditMapper.map(propertyDto, entity))
                .map(propertyRepository::saveAndFlush)
                .map(propertyReadMapper::map);
    }

    @Transactional
    public boolean delete(Long id) {
        return propertyRepository.findById(id).map(
                        entity -> {
                            propertyRepository.delete(entity);
                            propertyRepository.flush();
                            return true;
                        })
                .orElse(false);
    }
}
