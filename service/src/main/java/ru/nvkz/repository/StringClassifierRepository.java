package ru.nvkz.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.nvkz.entity.StringClassifier;

import java.util.List;

public interface StringClassifierRepository extends JpaRepository<StringClassifier, Long> {

    @EntityGraph(attributePaths = "property")
    List<StringClassifier> findByPropertyId(Long propertyId);
}
