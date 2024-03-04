package ru.nvkz.dao;

import ru.nvkz.entity.Order;

import javax.persistence.EntityManager;

public class OrderRepository extends RepositoryBase<Long, Order> {

    public OrderRepository(EntityManager entityManager) {
        super(Order.class, entityManager);
    }
}
