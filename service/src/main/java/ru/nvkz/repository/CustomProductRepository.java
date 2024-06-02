package ru.nvkz.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.nvkz.entity.Product;
import ru.nvkz.filter.ProductFilter;

public interface CustomProductRepository {

    Page<Product> findAllDistinctByProductFilter(ProductFilter productFilter, Long categoryId, Pageable pageable);
}
