package ru.nvkz.repository;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import ru.nvkz.entity.ProductOrder;

@Repository
public class ProductOrderRepository extends RepositoryBase<Long, ProductOrder> {

    public ProductOrderRepository(EntityManager entityManager) {
        super(ProductOrder.class, entityManager);
    }
}
