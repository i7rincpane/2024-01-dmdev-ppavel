package ru.nvkz.dao;

import ru.nvkz.entity.ProductProperty;

import javax.persistence.EntityManager;

public class ProductPropertyRepository extends RepositoryBase<Long, ProductProperty> {

    public ProductPropertyRepository(EntityManager entityManager) {
        super(ProductProperty.class, entityManager);
    }
}
