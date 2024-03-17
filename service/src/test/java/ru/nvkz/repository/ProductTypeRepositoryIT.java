package ru.nvkz.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import ru.nvkz.entity.ProductType;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@RequiredArgsConstructor
class ProductTypeRepositoryIT extends RepositoryBaseIT {

    private final ProductTypeRepository repository;

    @Test
    void save() {
        ProductType productType1 = getProductType("test-name1");
        ProductType productType2 = getProductType("test-name2");
        productType2.setParent(productType1);

        repository.save(productType1);
        repository.save(productType2);

        assertNotNull(productType1.getId());
        assertNotNull(productType1.getSubProductTypes().get(0).getId());
    }

    @Test
    void delete() {
        ProductType productType = getProductType("test-name");
        entityManager.persist(productType);

        repository.delete(productType);

        ProductType productTypeActual = entityManager.find(ProductType.class, productType.getId());
        assertNull(productTypeActual);
    }

    @Test
    void update() {
        ProductType productTypeExpected = getProductType("testName");
        entityManager.persist(productTypeExpected);
        entityManager.flush();
        entityManager.clear();
        productTypeExpected.setName("updated");

        repository.update(productTypeExpected);
        entityManager.flush();
        entityManager.clear();

        ProductType productTypeActual = entityManager.find(ProductType.class, productTypeExpected.getId());
        assertThat(productTypeActual.getName()).isEqualTo(productTypeExpected.getName());
    }

    @Test
    void findById() {
        ProductType productType = getProductType("test-name1");
        ProductType productTypeExpected = getProductType("test-name2");
        entityManager.persist(productType);
        entityManager.persist(productTypeExpected);
        entityManager.flush();
        entityManager.clear();

        Optional<ProductType> productTypeActual = repository.findById(productTypeExpected.getId());

        assertThat(productTypeActual).isPresent();
        assertThat(productTypeActual.get().getName()).isEqualTo(productTypeExpected.getName());
    }

    @Test
    void findAll() {
        ProductType productType1 = getProductType("test-name1");
        ProductType productType2 = getProductType("test-name2");
        ProductType productType3 = getProductType("test-name3");
        entityManager.persist(productType1);
        entityManager.persist(productType2);
        entityManager.persist(productType3);
        entityManager.flush();
        entityManager.clear();

        List<ProductType> productTypeActualBatch = repository.findAll();

        assertThat(productTypeActualBatch).hasSize(3);
        List<Integer> productTypeIdBathc = productTypeActualBatch.stream()
                .map(ProductType::getId)
                .toList();
        assertThat(productTypeIdBathc).contains(productType1.getId(),
                productType2.getId(),
                productType3.getId());
    }

    @Test
    void findAllProductTypeByProductTypeName() {
        ProductType productType1 = getProductType("test-name1");
        ProductType productType2 = getProductType("test-name2");
        ProductType productTypeExpected = getProductType("productTypeExpected");
        entityManager.persist(productType1);
        entityManager.persist(productType2);
        entityManager.persist(productTypeExpected);
        entityManager.flush();
        entityManager.clear();

        List<ProductType> productTypeActualBatch = repository.findAllByProductTypeName(productTypeExpected.getName());

        assertThat(productTypeActualBatch).hasSize(1);
        assertThat(productTypeActualBatch.get(0).getName()).contains(productTypeExpected.getName());
    }

    private ProductType getProductType(String name) {
        return ProductType.builder()
                .name(name)
                .build();
    }
}