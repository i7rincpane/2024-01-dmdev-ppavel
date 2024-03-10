package ru.nvkz.repository;

import org.springframework.stereotype.Repository;
import ru.nvkz.entity.ProductProperty;

import javax.persistence.EntityManager;

@Repository
public class ProductPropertyRepository extends RepositoryBase<Long, ProductProperty> {

    public ProductPropertyRepository(EntityManager entityManager) {
        super(ProductProperty.class, entityManager);
    }
}
