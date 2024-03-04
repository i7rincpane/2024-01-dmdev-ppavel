package ru.nvkz.dao;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nvkz.entity.Product;
import ru.nvkz.entity.ProductProperty;
import ru.nvkz.entity.ProductType;
import ru.nvkz.entity.Property;
import ru.nvkz.filter.ProductFilter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class ProductRepositoryTestIT extends RepositoryBaseTestIT {

    private ProductRepository repository;

    @BeforeEach
    @Override
    void initRepository() {
        repository = new ProductRepository(session);
    }

    @Override
    void save() {
        ProductType productType = getProductType("productType");
        session.save(productType);
        Product productExpected = getProduct("product", 1, productType);

        repository.save(productExpected);

        assertNotNull(productExpected.getId());
    }

    @Override
    void delete() {
        ProductType productType = getProductType("productType");
        session.save(productType);
        Product productExpected = getProduct("productExpected", 1, productType);
        session.save(productExpected);

        repository.delete(productExpected.getId());

        Product ProductActual = session.get(Product.class, productExpected.getId());
        assertNull(ProductActual);
    }

    @Override
    void update() {
        ProductType productType = getProductType("productType");
        session.save(productType);
        Product productExpected = getProduct("product", 1, productType);
        session.save(productExpected);
        session.flush();
        session.clear();
        productExpected.setName("updated");

        repository.update(productExpected);
        session.flush();
        session.clear();

        Product ProductActual = session.get(Product.class, productExpected.getId());
        assertThat(ProductActual.getName()).isEqualTo(productExpected.getName());
    }

    @Override
    void findById() {
        ProductType productType = getProductType("productType");
        session.save(productType);
        Product product1 = getProduct("product1", 1, productType);
        Product productExpected = getProduct("productExpected", 2, productType);
        session.save(product1);
        session.save(productExpected);
        session.flush();
        session.clear();

        Optional<Product> productActual = repository.findById(productExpected.getId());

        assertThat(productActual).isPresent();
        assertThat(productActual.get().getName()).isEqualTo(productExpected.getName());
    }

    @Override
    void findAll() {
        ProductType productType = getProductType("productType");
        session.save(productType);
        Product product1 = getProduct("product1", 1, productType);
        Product product2 = getProduct("product2", 2, productType);
        Product product3 = getProduct("product2", 3, productType);
        session.save(product1);
        session.save(product2);
        session.save(product3);
        session.flush();
        session.clear();

        List<Product> productActualBatch = repository.findAll();

        assertThat(productActualBatch).hasSize(3);
        List<Long> productActualIdBatch = productActualBatch.stream()
                .map(Product::getId)
                .toList();
        assertThat(productActualIdBatch).contains(product1.getId(),
                product2.getId(),
                product3.getId());
    }

    @Test
    void findAllDistinctByProductFilterAllParams() {
        ProductType productType = getProductType("productType");
        session.save(productType);
        ProductType productType1 = getProductType("productType1");
        session.save(productType1);
        Product product = getProduct("product", 1, productType, "producer1", 5000.0);
        Product product1 = getProduct("product1", 2, productType, "producer1", 6000.);
        Product product2 = getProduct("product2", 3, productType, "producer2", 5555.0);
        Product product3 = getProduct("product3", 4, productType1, "producer3", 3000.0);
        Product product4 = getProduct("product4", 5, productType, "producer1", 10000.0);
        session.save(product);
        session.save(product1);
        session.save(product2);
        session.save(product3);
        session.save(product4);
        Property property = getProperty("propertyName", "propertyValue");
        Property property1 = getProperty("propertyName1", "propertyValue1");
        Property property2 = getProperty("propertyName2", "propertyValue2");
        session.save(property);
        session.save(property1);
        session.save(property2);
        ProductProperty productProperty = getProductProperty(product, property);
        ProductProperty productProperty1 = getProductProperty(product, property1);
        ProductProperty productProperty2 = getProductProperty(product1, property);
        ProductProperty productProperty4 = getProductProperty(product2, property1);
        ProductProperty productProperty5 = getProductProperty(product3, property2);
        ProductProperty productProperty6 = getProductProperty(product4, property);
        session.save(productProperty);
        session.save(productProperty1);
        session.save(productProperty2);
        session.save(productProperty4);
        session.save(productProperty5);
        session.save(productProperty6);
        session.flush();
        session.clear();
        ProductFilter productFilter = ProductFilter.builder()
                .productTypeId(productType.getId())
                .producerNames(List.of("producer1"))
                .propertyIdBatch(List.of(property.getId(), property1.getId()))
                .priceFrom(new BigDecimal(2000.0))
                .priceBy(new BigDecimal(9999.0))
                .build();

        List<Product> productActualBatch = repository.findAllDistinctByProductFilter(productFilter);
        Assertions.assertThat(productActualBatch).hasSize(2);

        List<String> productNameActualBatch = productActualBatch.stream().map(Product::getName).toList();

        assertThat(productNameActualBatch).contains(product.getName(),
                product1.getName());
    }

    private Product getProduct(String name, int code, ProductType productType) {
        return Product.builder()
                .code(code)
                .name(name)
                .price(new BigDecimal(1))
                .model("model")
                .producer("producer")
                .count(5)
                .productType(productType)
                .build();
    }

    private static Product getProduct(String name, int code, ProductType productType, String producer, Double price) {
        return Product.builder()
                .code(code)
                .name(name)
                .price(new BigDecimal(price))
                .model("model")
                .producer(producer)
                .count(5)
                .productType(productType)
                .build();
    }

    private ProductType getProductType(String name) {
        return ProductType.builder()
                .name(name)
                .build();
    }

    private Property getProperty(String name, String value) {
        return Property.builder()
                .name(name)
                .value(value)
                .unit("unit")
                .build();
    }

    private ProductProperty getProductProperty(Product product, Property property) {
        return ProductProperty.builder()
                .product(product)
                .property(property)
                .build();
    }

}