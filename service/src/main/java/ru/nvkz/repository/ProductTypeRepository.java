package ru.nvkz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nvkz.entity.ProductType;

import java.util.List;

public interface ProductTypeRepository extends JpaRepository<ProductType, Integer> {
    List<ProductType> findAllByName(String name);
}
