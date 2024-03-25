package ru.nvkz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nvkz.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
