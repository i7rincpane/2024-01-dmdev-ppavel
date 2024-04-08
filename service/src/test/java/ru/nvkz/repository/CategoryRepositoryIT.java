package ru.nvkz.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import ru.nvkz.IntegrationTestBase;
import ru.nvkz.entity.Category;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
class CategoryRepositoryIT extends IntegrationTestBase {

    private final CategoryRepository repository;

    @Test
    void findAllProductTypeByProductTypeName() {
        String electricHobTypeNameExpected = "Электрическая варочная поверхность";
        List<Category> categoryActualBatch = repository.findAllByName(electricHobTypeNameExpected);

        assertThat(categoryActualBatch).hasSize(1);
        assertThat(categoryActualBatch.get(0).getName()).contains(electricHobTypeNameExpected);
    }
}