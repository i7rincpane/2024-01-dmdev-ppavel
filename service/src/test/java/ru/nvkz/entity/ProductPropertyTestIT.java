package ru.nvkz.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class ProductPropertyTestIT extends BaseHibernateCrudTestIT {

    @Test
    @Override
    void create() {
        ProductType productType = getProductType("productType");
        session.save(productType);
        Product product = getProduct("product", productType);
        session.save(product);
        Property property = getProperty("property");
        session.save(property);
        ProductProperty productPropertyExpected = getProductProperty(product, property);

        session.save(productPropertyExpected);

        assertNotNull(productPropertyExpected.getId());
    }

    @Test
    @Override
    void read() {
        ProductType productType = getProductType("productType");
        session.save(productType);
        Product product = getProduct("product", productType);
        session.save(product);
        Property property = getProperty("property");
        session.save(property);
        Property propertyExpected = getProperty("propertyExpected");
        session.save(propertyExpected);
        ProductProperty productProperty = getProductProperty(product, property);
        ProductProperty productPropertyExpected = getProductProperty(product, propertyExpected);
        session.save(productProperty);
        session.save(productPropertyExpected);
        session.flush();
        session.clear();

        ProductProperty productPropertyActual = session.get(ProductProperty.class, productPropertyExpected.getId());

        assertThat(productPropertyActual.getProperty().getName()).isEqualTo(propertyExpected.getName());
    }

    @Test
    @Override
    void update() {
        ProductType productType = getProductType("productType");
        session.save(productType);
        Product product = getProduct("product", productType);
        session.save(product);
        Property property = getProperty("property");
        session.save(property);
        Property propertyExpected = getProperty("propertyExpected");
        propertyExpected.setValue("11");
        session.save(property);
        session.save(propertyExpected);
        ProductProperty productProperty = getProductProperty(product, property);
        session.save(productProperty);
        session.flush();
        session.clear();
        productProperty.setProperty(propertyExpected);

        session.update(productProperty);
        session.flush();
        session.clear();

        ProductProperty productPropertyActual = session.get(ProductProperty.class, productProperty.getId());
        assertThat(productPropertyActual.getProperty().getValue()).isEqualTo(propertyExpected.getValue());
    }

    @Test
    @Override
    void delete() {
        ProductType productType = getProductType("productType");
        session.save(productType);
        Product product = getProduct("product", productType);
        session.save(product);
        Property property = getProperty("property");
        session.save(property);
        ProductProperty productProperty = getProductProperty(product, property);
        session.save(productProperty);

        session.delete(productProperty);
        session.flush();

        ProductProperty productPropertyActual = session.get(ProductProperty.class, productProperty.getId());
        assertNull(productPropertyActual);
    }

    private Product getProduct(String name, ProductType productType) {
        return Product.builder()
                .code(1)
                .name(name)
                .color("color")
                .price(new BigDecimal(1))
                .model("model")
                .producer("producer")
                .count(5)
                .productType(productType)
                .build();
    }

    private Property getProperty(String name) {
        return Property.builder()
                .name(name)
                .value("1")
                .unit("unit")
                .build();
    }

    private ProductType getProductType(String name) {
        return ProductType.builder()
                .name(name)
                .build();
    }

    private ProductProperty getProductProperty(Product product, Property property) {
        return ProductProperty.builder()
                .product(product)
                .property(property)
                .build();
    }
}
