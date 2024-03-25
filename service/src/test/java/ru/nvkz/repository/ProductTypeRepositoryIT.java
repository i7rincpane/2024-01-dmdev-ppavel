package ru.nvkz.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import ru.nvkz.entity.ProductType;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
class ProductTypeRepositoryIT extends RepositoryBaseIT {

    private final ProductTypeRepository repository;

    @Test
    void findAllProductTypeByProductTypeName() {
        String electricHobTypeNameExpected = "Электрическая варочная поверхность";
        List<ProductType> productTypeActualBatch = repository.findAllByName(electricHobTypeNameExpected);

        assertThat(productTypeActualBatch).hasSize(1);
        assertThat(productTypeActualBatch.get(0).getName()).contains(electricHobTypeNameExpected);
    }
}