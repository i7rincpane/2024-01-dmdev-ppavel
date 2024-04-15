package ru.nvkz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.nvkz.entity.Producer;

import java.util.List;

@Repository
public interface ProducerRepository extends JpaRepository<Producer, Integer> {

    @Query(value = "select producer from Producer producer " +
            "join producer.products p " +
            "join p.category c " +
            "where c.id = :categoryId " +
            "group by producer.id ")
    List<Producer> findAllByCategoryId(Long categoryId);
}
