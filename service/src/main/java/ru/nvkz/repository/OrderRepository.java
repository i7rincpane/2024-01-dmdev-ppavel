package ru.nvkz.repository;

import org.springframework.stereotype.Repository;
import ru.nvkz.entity.Order;

import javax.persistence.EntityManager;

@Repository
public class OrderRepository extends RepositoryBase<Long, Order> {

    public OrderRepository(EntityManager entityManager) {
        super(Order.class, entityManager);
    }
}
