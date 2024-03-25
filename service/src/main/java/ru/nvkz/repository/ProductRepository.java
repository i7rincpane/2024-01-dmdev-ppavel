package ru.nvkz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nvkz.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, CustomProductRepository {
}
