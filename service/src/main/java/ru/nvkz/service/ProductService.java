package ru.nvkz.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nvkz.dto.ProductCreateEditDto;
import ru.nvkz.dto.ProductReadDto;
import ru.nvkz.entity.Product;
import ru.nvkz.filter.ProductFilter;
import ru.nvkz.mapper.Mapper;
import ru.nvkz.mapper.ProductCreateEditMapper;
import ru.nvkz.mapper.ProductReadMapper;
import ru.nvkz.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductReadMapper productReadMapper;
    private final ProductRepository productRepository;
    private final ProductPropertyService productPropertyService;
    private final ProductCreateEditMapper productCreateEditMapper;

    public Optional<ProductReadDto> findById(Long id) {
        return this.findById(id, productReadMapper);
    }

    public <T> Optional<T> findById(Long id, Mapper<Product, T> mapper) {
        return productRepository.findById(id)
                .map(mapper::map);
    }

    public List<ProductReadDto> findAllDistinctByProductFilter(ProductFilter productFilter, Long categoryId) {
        return productRepository.findAllDistinctByProductFilter(productFilter, categoryId).stream()
                .map(productReadMapper::map)
                .toList();
    }

    @Transactional
    public Optional<ProductReadDto> update(Long id, ProductCreateEditDto product) {
        productPropertyService.updateAll(product.getProductProperties());
        return Optional.ofNullable(ProductReadDto.builder().build());
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
}
