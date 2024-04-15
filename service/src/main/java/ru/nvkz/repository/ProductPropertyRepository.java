package ru.nvkz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nvkz.entity.ProductProperty;

import java.util.List;

@Repository
public interface ProductPropertyRepository extends JpaRepository<ProductProperty, Long> {

    List<ProductProperty> findByProductId(Long productId);
}
