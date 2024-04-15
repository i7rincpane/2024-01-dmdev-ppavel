package ru.nvkz.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.nvkz.entity.Property;

import java.util.List;
import java.util.Optional;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long>, CustomPropertyRepository {

    @Override
    @EntityGraph(attributePaths = {"category"})
    Optional<Property> findById(Long id);

    @EntityGraph(attributePaths = {"category"})
    List<Property> findByCategoryId(Long categoryId);

}
