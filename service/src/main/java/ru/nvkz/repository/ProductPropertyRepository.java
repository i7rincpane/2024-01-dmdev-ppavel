package ru.nvkz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nvkz.entity.ProductProperty;

@Repository
public interface ProductPropertyRepository extends JpaRepository<ProductProperty, Long> {
}
