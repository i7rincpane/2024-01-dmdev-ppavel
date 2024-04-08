package ru.nvkz.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.nvkz.dto.CategoryPathElement;
import ru.nvkz.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Optional<Category> findById(Integer id);

    @EntityGraph(attributePaths = {"parent"})
    List<Category> findAllByParentId(Integer id);

    List<Category> findAllByName(String name);

    @Query(nativeQuery = true,
            value = "WITH RECURSIVE r(id, name, parent_id, level) AS " +
                    "                   (SELECT id, name, parent_id, 1 " +
                    "                    FROM category " +
                    "                    WHERE id = :parentId " +
                    "                    UNION ALL " +
                    "                    SELECT t.id, t.name, t.parent_id, level+1 " +
                    "                    FROM category t, r " +
                    "                    WHERE t.id = r.parent_id ) " +
                    "SELECT parents.* FROM ( " +
                    "                                                     SELECT id, name, level, parent_id parentId FROM r " +
                    "                                                     ORDER BY level DESC) parents")
    List<CategoryPathElement> findAllPathElementByParentId(Integer parentId);

}
