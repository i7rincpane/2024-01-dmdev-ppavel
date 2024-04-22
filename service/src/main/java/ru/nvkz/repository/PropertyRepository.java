package ru.nvkz.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ru.nvkz.entity.Property;

import java.util.List;
import java.util.Optional;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long>, CustomPropertyRepository {

    @Override
    @EntityGraph(attributePaths = {"category", "category.parent"})
    Optional<Property> findById(Long id);

    @EntityGraph(attributePaths = {"category", "category.parent"})
    List<Property> findByCategoryId(Long categoryId);

    @Query(value = """
            select p from Property p 
            join p.productProperties pp 
            join fetch p.category c
            join fetch c.parent
            where pp.product.id = :productId
            """)
    List<Property> findByProductId(Long productId);
}
