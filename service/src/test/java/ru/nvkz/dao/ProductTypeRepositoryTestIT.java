package ru.nvkz.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nvkz.entity.ProductType;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class ProductTypeRepositoryTestIT extends RepositoryBaseTestIT<ProductTypeRepository> {

    @BeforeEach
    void initRepository() {
        repository = new ProductTypeRepository(session);
    }

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
        session.save(productType);

        repository.delete(productType.getId());

        ProductType productTypeActual = session.get(ProductType.class, productType.getId());
        assertNull(productTypeActual);
    }

    @Test
    void update() {
        ProductType productTypeExpected = getProductType("testName");
        session.save(productTypeExpected);
        session.flush();
        session.clear();
        productTypeExpected.setName("updated");

        repository.update(productTypeExpected);
        session.flush();
        session.clear();

        ProductType productTypeActual = session.get(ProductType.class, productTypeExpected.getId());
        assertThat(productTypeActual.getName()).isEqualTo(productTypeExpected.getName());
    }

    @Test
    void findById() {
        ProductType productType = getProductType("test-name1");
        ProductType productTypeExpected = getProductType("test-name2");
        session.save(productType);
        session.save(productTypeExpected);
        session.flush();
        session.clear();

        Optional<ProductType> productTypeActual = repository.findById(productTypeExpected.getId());

        assertThat(productTypeActual).isPresent();
        assertThat(productTypeActual.get().getName()).isEqualTo(productTypeExpected.getName());
    }

    @Test
    void findAll() {
        ProductType productType1 = getProductType("test-name1");
        ProductType productType2 = getProductType("test-name2");
        ProductType productType3 = getProductType("test-name3");
        session.save(productType1);
        session.save(productType2);
        session.save(productType3);
        session.flush();
        session.clear();

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
        session.save(productType1);
        session.save(productType2);
        session.save(productTypeExpected);
        session.flush();
        session.clear();

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