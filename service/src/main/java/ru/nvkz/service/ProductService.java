package ru.nvkz.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.nvkz.dto.ProductCreateEditDto;
import ru.nvkz.dto.ProductReadDto;
import ru.nvkz.entity.Product;
import ru.nvkz.filter.ProductFilter;
import ru.nvkz.mapper.Mapper;
import ru.nvkz.mapper.ProductCreateEditMapper;
import ru.nvkz.mapper.ProductReadMapper;
import ru.nvkz.repository.ProductRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductReadMapper productReadMapper;
    private final ProductRepository productRepository;
    private final ProductPropertyService productPropertyService;
    private final ProductCreateEditMapper productCreateEditMapper;
    private final ImageService imageService;

    public Optional<ProductReadDto> findById(Long id) {
        return this.findById(id, productReadMapper);
    }

    public <T> Optional<T> findById(Long id, Mapper<Product, T> mapper) {
        return productRepository.findById(id)
                .map(mapper::map);
    }

    public Page<ProductReadDto> findAllDistinctByProductFilter(ProductFilter productFilter, Long categoryId, Pageable pageable) {
        return productRepository.findAllDistinctByProductFilter(productFilter, categoryId, pageable)
                .map(productReadMapper::map);
    }

    @Transactional
    public Optional<ProductReadDto> update(Long id, ProductCreateEditDto productCreateEditDto) {
        productPropertyService.updateAll(productCreateEditDto.getProductProperties());
        return productRepository.findById(id)
                .map(entity -> productCreateEditMapper.map(productCreateEditDto, entity))
                .map(productRepository::saveAndFlush)
                .map(productReadMapper::map);
    }


    @Transactional
    public ProductReadDto create(ProductCreateEditDto productDto) {
        return Optional.of(productDto)
                .map(productCreateEditMapper::map)
                .map(productRepository::save)
                .map(productReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public boolean delete(Long id) {
        return productRepository.findById(id).map(
                        entity -> {
                            productRepository.delete(entity);
                            productRepository.flush();
                            return true;
                        })
                .orElse(false);
    }

    public Optional<byte[]> findImage(Long id) {
        return productRepository.findById(id)
                .map(Product::getImage)
                .filter(StringUtils::hasText)
                .flatMap(imageService::get);
    }
}
