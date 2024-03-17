package ru.nvkz.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import ru.nvkz.entity.Product;
import ru.nvkz.entity.ProductProperty;
import ru.nvkz.entity.ProductType;
import ru.nvkz.entity.Property;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@RequiredArgsConstructor
class PropertyRepositoryIT extends RepositoryBaseIT {

    private final PropertyRepository repository;

    @Test
    @Override
    void save() {
        Property property = getProperty("test-name1");

        repository.save(property);

        assertNotNull(property.getId());
    }

    @Test
    @Override
    void findById() {
        Property property = getProperty("test-name1");
        Property propertyExpected = getProperty("test-name2");
        entityManager.persist(property);
        entityManager.persist(propertyExpected);
        entityManager.flush();
        entityManager.clear();

        Optional<Property> propertyActual = repository.findById(propertyExpected.getId());

        assertThat(propertyActual).isPresent();
        assertThat(propertyActual.get().getName()).isEqualTo(propertyExpected.getName());
    }

    @Test
    @Override
    void update() {
        Property propertyExpected = getProperty("testName");
        entityManager.persist(propertyExpected);
        entityManager.flush();
        entityManager.clear();
        propertyExpected.setName("updated");

        repository.update(propertyExpected);
        entityManager.flush();
        entityManager.clear();

        Property propertyActual = entityManager.find(Property.class, propertyExpected.getId());
        assertThat(propertyActual.getName()).isEqualTo(propertyExpected.getName());
    }

    @Test
    @Override
    void delete() {
        Property property = getProperty("test-name");
        entityManager.persist(property);

        repository.delete(property);

        Property propertyActual = entityManager.find(Property.class, property.getId());
        assertNull(propertyActual);
    }

    @Override
    void findAll() {
        Property property1 = getProperty("test-name1");
        Property property2 = getProperty("test-name2");
        Property property3 = getProperty("test-name3");
        entityManager.persist(property1);
        entityManager.persist(property2);
        entityManager.persist(property3);
        entityManager.flush();
        entityManager.clear();

        List<Property> propertyActualBatch = repository.findAll();

        assertThat(propertyActualBatch).hasSize(3);
        List<Long> propertyIdBatch = propertyActualBatch.stream()
                .map(Property::getId)
                .toList();
        assertThat(propertyIdBatch).contains(property1.getId(),
                property2.getId(),
                property3.getId());

    }

    @Test
    void findAllDistinctPropertyByProductTypeId() {
        ProductType productType = getProductType("productType");
        entityManager.persist(productType);
        ProductType productType1 = getProductType("productType1");
        entityManager.persist(productType1);
        Product product = getProduct("product", 1, productType, "producer1", 5000.0);
        Product product1 = getProduct("product1", 2, productType, "producer1", 6000.);
        Product product2 = getProduct("product2", 3, productType, "producer2", 5555.0);
        Product product3 = getProduct("product3", 4, productType1, "producer3", 3000.0);
        Product product4 = getProduct("product4", 5, productType, "producer1", 10000.0);
        entityManager.persist(product);
        entityManager.persist(product1);
        entityManager.persist(product2);
        entityManager.persist(product3);
        entityManager.persist(product4);
        Property property = getProperty("propertyName", "propertyValue");
        Property property1 = getProperty("propertyName1", "propertyValue1");
        Property property2 = getProperty("propertyName2", "propertyValue2");
        entityManager.persist(property);
        entityManager.persist(property1);
        entityManager.persist(property2);
        ProductProperty productProperty = getProductProperty(product, property);
        ProductProperty productProperty1 = getProductProperty(product, property1);
        ProductProperty productProperty2 = getProductProperty(product1, property);
        ProductProperty productProperty4 = getProductProperty(product2, property1);
        ProductProperty productProperty5 = getProductProperty(product3, property2);
        ProductProperty productProperty6 = getProductProperty(product4, property);
        entityManager.persist(productProperty);
        entityManager.persist(productProperty1);
        entityManager.persist(productProperty2);
        entityManager.persist(productProperty4);
        entityManager.persist(productProperty5);
        entityManager.persist(productProperty6);
        entityManager.flush();
        entityManager.clear();

        List<Property> propertyActualBatch = repository.findAllDistinctPropertyByProductTypeId(productType.getId());

        assertThat(propertyActualBatch).hasSize(2);
        List<Long> propertyIdBatch = propertyActualBatch.stream()
                .map(Property::getId)
                .toList();
        assertThat(propertyIdBatch).contains(property.getId(),
                property1.getId());
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

    private Property getProperty(String name) {
        return Property.builder()
                .name(name)
                .value("1")
                .unit("unit")
                .build();
    }
}
