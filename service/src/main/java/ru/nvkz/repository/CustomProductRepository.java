package ru.nvkz.repository;

import ru.nvkz.entity.Product;
import ru.nvkz.filter.ProductFilter;

import java.util.List;

public interface CustomProductRepository {
    List<Product> findAllDistinctByProductFilter(ProductFilter productFilter);
}
