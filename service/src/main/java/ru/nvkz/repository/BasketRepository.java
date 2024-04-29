package ru.nvkz.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nvkz.entity.Basket;

import java.util.Optional;

@Repository
public interface BasketRepository extends JpaRepository<Basket, Long> {

    @EntityGraph(attributePaths = {"user"})
    Optional<Basket> findByUserId(Long userId);

    @EntityGraph(attributePaths = {"user"})
    Optional<Basket> findById(Long id);

}
