package ru.nvkz.entity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class ProductTypeTestIT extends BaseHibernateCrudTestIT {

    @Test
    @Override
    void create() {
        ProductType productType1 = getProductType("test-name1");
        ProductType productType2 = getProductType("test-name2");
        productType2.setParent(productType1);

        session.save(productType1);
        session.save(productType2);

        assertNotNull(productType1.getId());
        assertNotNull(productType1.getSubProductTypes().get(0).getId());
    }

    @Test
    @Override
    void read() {
        ProductType productType1 = getProductType("test-name1");
        ProductType productTypeExpected = getProductType("test-name2");
        session.save(productType1);
        session.save(productTypeExpected);
        session.flush();
        session.clear();

        ProductType categoryActual = session.get(ProductType.class, productTypeExpected.getId());

        assertThat(categoryActual.getName()).isEqualTo(productTypeExpected.getName());
    }

    @Test
    @Override
    void update() {
        ProductType productTypeExpected = getProductType("testName");
        session.save(productTypeExpected);
        session.flush();
        session.clear();
        productTypeExpected.setName("updated");

        session.update(productTypeExpected);
        session.flush();
        session.clear();

        ProductType productTypeActual = session.get(ProductType.class, productTypeExpected.getId());
        assertThat(productTypeActual.getName()).isEqualTo(productTypeExpected.getName());
    }

    @Test
    @Override
    void delete() {
        ProductType productType = getProductType("test-name");
        session.save(productType);

        session.delete(productType);
        session.flush();

        ProductType productTypeActual = session.get(ProductType.class, productType.getId());
        assertNull(productTypeActual);
    }

    private ProductType getProductType(String name) {
        return ProductType.builder()
                .name(name)
                .build();
    }
}
