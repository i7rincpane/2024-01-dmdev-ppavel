package ru.nvkz.repository;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import ru.nvkz.entity.Order;

@Repository
public class OrderRepository extends RepositoryBase<Long, Order> {

    public OrderRepository(EntityManager entityManager) {
        super(Order.class, entityManager);
    }
}
