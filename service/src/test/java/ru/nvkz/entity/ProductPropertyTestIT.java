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
        Property property1 = getProperty("property1");
        session.save(property1);
        Property propertyExpected = getProperty("propertyExpected");
        session.save(propertyExpected);
        ProductProperty productProperty1 = getProductProperty(product, property1);
        ProductProperty productPropertyExpected = getProductProperty(product, propertyExpected);
        session.save(productProperty1);
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
        ProductProperty productPropertyExpected = getProductProperty(product, property);
        session.save(productPropertyExpected);
        session.flush();
        session.clear();
        productPropertyExpected.setValue("11");

        session.update(productPropertyExpected);
        session.flush();
        session.clear();

        ProductProperty productPropertyActual = session.get(ProductProperty.class, productPropertyExpected.getId());
        assertThat(productPropertyActual.getValue()).isEqualTo(productPropertyExpected.getValue());
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
                .value("1")
                .build();
    }
}
