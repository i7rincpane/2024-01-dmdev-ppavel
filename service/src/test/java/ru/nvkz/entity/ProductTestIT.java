package ru.nvkz.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class ProductTestIT extends BaseHibernateCrudTestIT {

    @Test
    @Override
    void create() {
        ProductType productType = getProductType("productType");
        session.save(productType);
        Product productExpected = getProduct("product", 1, productType);

        session.save(productExpected);

        assertNotNull(productExpected.getId());
    }

    @Test
    @Override
    void read() {
        ProductType productType = getProductType("productType");
        session.save(productType);
        Product product1 = getProduct("product1",1, productType);
        Product productExpected = getProduct("productExpected", 2, productType);
        session.save(product1);
        session.save(productExpected);
        session.flush();
        session.clear();

        Product productActual = session.get(Product.class, productExpected.getId());

        assertThat(productActual.getName()).isEqualTo(productExpected.getName());
    }

    @Test
    @Override
    void update() {
        ProductType productType = getProductType("productType");
        session.save(productType);
        Product productExpected = getProduct("product", 1, productType);
        session.save(productExpected);
        session.flush();
        session.clear();
        productExpected.setName("updated");

        session.update(productExpected);
        session.flush();
        session.clear();

        Product ProductActual = session.get(Product.class, productExpected.getId());
        assertThat(ProductActual.getName()).isEqualTo(productExpected.getName());
    }

    @Test
    @Override
    void delete() {
        ProductType productType = getProductType("productType");
        session.save(productType);
        Product productExpected = getProduct("productExpected", 1, productType);
        session.save(productExpected);

        session.delete(productExpected);
        session.flush();

        Product ProductActual = session.get(Product.class, productExpected.getId());
        assertNull(ProductActual);
    }

    private Product getProduct(String name, int code, ProductType productType) {
        return Product.builder()
                .code(code)
                .name(name)
                .color("color")
                .price(new BigDecimal(1))
                .model("model")
                .producer("producer")
                .count(5)
                .productType(productType)
                .build();
    }

    private ProductType getProductType(String name) {
        return ProductType.builder()
                .name(name)
                .build();
    }
}
