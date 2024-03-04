package ru.nvkz.dao;

import ru.nvkz.entity.ProductOrder;

import javax.persistence.EntityManager;

public class ProductOrderRepository extends RepositoryBase<Long, ProductOrder> {

    public ProductOrderRepository(EntityManager entityManager) {
        super(ProductOrder.class, entityManager);
    }
}
