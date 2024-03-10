package ru.nvkz.repository;

import org.springframework.stereotype.Repository;
import ru.nvkz.entity.ProductOrder;

import javax.persistence.EntityManager;

@Repository
public class ProductOrderRepository extends RepositoryBase<Long, ProductOrder> {

    public ProductOrderRepository(EntityManager entityManager) {
        super(ProductOrder.class, entityManager);
    }
}
