package ru.nvkz.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nvkz.dto.ProductPropertyCreateEditDto;
import ru.nvkz.dto.ProductPropertyReadDto;
import ru.nvkz.entity.ProductProperty;
import ru.nvkz.mapper.ProductPropertyCreateEditMapper;
import ru.nvkz.mapper.ProductPropertyReadMapper;
import ru.nvkz.repository.ProductPropertyRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductPropertyService {

    private final ProductPropertyRepository productPropertyRepository;
    private final ProductPropertyReadMapper productPropertyReadMapper;

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

    public List<ProductPropertyReadDto> findByProductId(Long productId) {
        List<ProductProperty> productProperties = productPropertyRepository.findByProductId(productId);
        return productProperties
                .stream()
                .map(productPropertyReadMapper::map)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<ProductPropertyReadDto> updateAll(List<ProductPropertyCreateEditDto> productProperties) {
        return productProperties.stream().map(productProperty -> update(productProperty.getId(), productProperty)).map(Optional::orElseThrow).toList();
    }


    @Transactional
    public List<ProductPropertyReadDto> createAll(List<ProductPropertyCreateEditDto> productPropertis) {
        List<ProductProperty> result = productPropertyRepository.saveAll(productPropertis.stream().map(productPropertyCreateEditMapper::map).collect(Collectors.toList()));
        return result.stream().map(productPropertyReadMapper::map).collect(Collectors.toList());
    }
}
