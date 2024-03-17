package ru.nvkz.repository;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import ru.nvkz.entity.ProductProperty;

@Repository
public class ProductPropertyRepository extends RepositoryBase<Long, ProductProperty> {

    public ProductPropertyRepository(EntityManager entityManager) {
        super(ProductProperty.class, entityManager);
    }
}
