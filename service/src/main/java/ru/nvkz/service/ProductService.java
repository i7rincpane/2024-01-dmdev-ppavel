package ru.nvkz.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nvkz.dto.ProductCreateEditDto;
import ru.nvkz.dto.ProductReadDto;
import ru.nvkz.filter.ProductFilter;
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

    public Optional<ProductReadDto> findById(Long id) {
        return productRepository.findById(id)
                .map(productReadMapper::map);
    }

    public List<ProductReadDto> findAllDistinctByProductFilter(ProductFilter productFilter, Long categoryId) {
        return productRepository.findAllDistinctByProductFilter(productFilter, categoryId).stream()
                .map(productReadMapper::map)
                .toList();
    }

    //TODO: придумать как мапить и сохранять..
    public Optional<ProductReadDto> update(Long id, ProductCreateEditDto product) {
        System.out.println(product);
        return Optional.ofNullable(ProductReadDto.builder().build());
    }

}
