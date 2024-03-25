package ru.nvkz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.nvkz.entity.Property;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long>, CustomPropertyRepository {
}
