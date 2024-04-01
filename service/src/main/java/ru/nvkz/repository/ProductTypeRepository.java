package ru.nvkz.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.nvkz.dto.ProductTypePathElement;
import ru.nvkz.entity.ProductType;

import java.util.List;
import java.util.Optional;

public interface ProductTypeRepository extends JpaRepository<ProductType, Integer> {

    Optional<ProductType> findById(Integer id);

    @EntityGraph(attributePaths = {"parent"})
    List<ProductType> findAllByParentId(Integer id);

    List<ProductType> findAllByName(String name);

    @Query(nativeQuery = true,
            value = "WITH RECURSIVE r(id, name, parent_id, level) AS\n" +
                    "                   (SELECT id, name, parent_id, 1\n" +
                    "                    FROM product_type\n" +
                    "                    WHERE id = :id\n" +
                    "                    UNION ALL\n" +
                    "                    SELECT t.id, t.name, t.parent_id, level+1\n" +
                    "                    FROM product_type t, r\n" +
                    "                    WHERE t.id = r.parent_id )\n" +
                    "SELECT parents.* FROM (\n" +
                    "                                                     SELECT id, name, level, parent_id parentId FROM r\n" +
                    "                                                     ORDER BY level DESC) parents")
    List<ProductTypePathElement> findAllPathElementByParentId(Integer id);
}
