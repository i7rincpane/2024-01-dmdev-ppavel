package ru.nvkz.repository;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.nvkz.entity.BasketProduct;

import java.util.List;

@Repository
public interface BasketProductRepository extends JpaRepository<BasketProduct, Long> {

    @EntityGraph(attributePaths = {"product"})
    List<BasketProduct> findAllByBasketId(Long basketId);

    @Query(value = "select bp from BasketProduct bp " +
            "join bp.product p " +
            "where bp.isSelected=true and bp.basket.id =:basketId and bp.count <= p.count ")
    @Lock(LockModeType.PESSIMISTIC_READ)
    List<BasketProduct> findAllByBasketIdAndIsSelectedTrueAndCountLessThanEqualProductCount(Long basketId);
}