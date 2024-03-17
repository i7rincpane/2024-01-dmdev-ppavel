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
class ProductPropertyRepositoryIT extends RepositoryBaseIT {

    private final ProductPropertyRepository repository;

    @Test
    @Override
    void save() {
        ProductType productType = getProductType("productType");
        entityManager.persist(productType);
        Product product = getProduct("product", 1, productType);
        entityManager.persist(product);
        Property property = getProperty("property");
        entityManager.persist(property);
        ProductProperty productPropertyExpected = getProductProperty(product, property);

        repository.save(productPropertyExpected);

        assertNotNull(productPropertyExpected.getId());
    }

    @Test
    @Override
    void findById() {
        ProductType productType = getProductType("productType");
        entityManager.persist(productType);
        Product product = getProduct("product", 1, productType);
        entityManager.persist(product);
        Property property = getProperty("property");
        entityManager.persist(property);
        Property propertyExpected = getProperty("propertyExpected");
        entityManager.persist(propertyExpected);
        ProductProperty productProperty = getProductProperty(product, property);
        ProductProperty productPropertyExpected = getProductProperty(product, propertyExpected);
        entityManager.persist(productProperty);
        entityManager.persist(productPropertyExpected);
        entityManager.flush();
        entityManager.clear();

        Optional<ProductProperty> productPropertyActual = repository.findById(productPropertyExpected.getId());

        assertThat(productPropertyActual).isPresent();
        assertThat(productPropertyActual.get().getProperty().getName()).isEqualTo(propertyExpected.getName());
    }

    @Test
    @Override
    void update() {
        ProductType productType = getProductType("productType");
        entityManager.persist(productType);
        Product product = getProduct("product", 1, productType);
        entityManager.persist(product);
        Property property = getProperty("property");
        entityManager.persist(property);
        Property propertyExpected = getProperty("propertyExpected");
        propertyExpected.setValue("11");
        entityManager.persist(property);
        entityManager.persist(propertyExpected);
        ProductProperty productProperty = getProductProperty(product, property);
        entityManager.persist(productProperty);
        entityManager.flush();
        entityManager.clear();
        productProperty.setProperty(propertyExpected);

        repository.update(productProperty);
        entityManager.flush();
        entityManager.clear();

        ProductProperty productPropertyActual = entityManager.find(ProductProperty.class, productProperty.getId());
        assertThat(productPropertyActual.getProperty().getValue()).isEqualTo(propertyExpected.getValue());
    }


    @Test
    @Override
    void delete() {
        ProductType productType = getProductType("productType");
        entityManager.persist(productType);
        Product product = getProduct("product", 1, productType);
        entityManager.persist(product);
        Property property = getProperty("property");
        entityManager.persist(property);
        ProductProperty productProperty = getProductProperty(product, property);
        entityManager.persist(productProperty);

        repository.delete(productProperty);
        entityManager.flush();

        ProductProperty productPropertyActual = entityManager.find(ProductProperty.class, productProperty.getId());
        assertNull(productPropertyActual);
    }


    @Test
    @Override
    void findAll() {
        ProductType productType = getProductType("productType");
        entityManager.persist(productType);
        Property property = getProperty("test-name1");
        entityManager.persist(property);
        Product product = getProduct("product1", 1, productType);
        Product product1 = getProduct("product2", 2, productType);
        Product product2 = getProduct("product2", 3, productType);
        entityManager.persist(product);
        entityManager.persist(product1);
        entityManager.persist(product2);
        ProductProperty productProperty = getProductProperty(product, property);
        ProductProperty productProperty1 = getProductProperty(product1, property);
        ProductProperty productProperty2 = getProductProperty(product2, property);
        entityManager.persist(productProperty);
        entityManager.persist(productProperty1);
        entityManager.persist(productProperty2);
        entityManager.flush();
        entityManager.clear();

        List<ProductProperty> productPropertyActualBatch = repository.findAll();

        assertThat(productPropertyActualBatch).hasSize(3);
        List<Long> productPropertyIdActualBatch = productPropertyActualBatch.stream()
                .map(ProductProperty::getId)
                .toList();
        assertThat(productPropertyIdActualBatch).contains(productProperty.getId(),
                productProperty1.getId(),
                productProperty2.getId());
    }

    @Test
    void checkIncrementPropertyCountPostPersistProductProperty() {
        ProductType productType = getProductType("productType");
        entityManager.persist(productType);
        Product product = getProduct("product", 1, productType);
        Product product1 = getProduct("product1", 2, productType);
        entityManager.persist(product);
        entityManager.persist(product1);
        Property property = getProperty("property");
        entityManager.persist(property);
        ProductProperty productProperty = getProductProperty(product, property);
        ProductProperty productProperty1 = getProductProperty(product1, property);

        repository.save(productProperty);
        repository.save(productProperty1);
        entityManager.flush();
        entityManager.clear();

        Integer propertyCountActual = entityManager.find(Property.class, property.getId()).getCount();
        assertThat(propertyCountActual).isEqualTo(2);
    }

    @Test
    void checkDecrementPropertyCountPostRemoveProductProperty() {
        ProductType productType = getProductType("productType");
        entityManager.persist(productType);
        Product product = getProduct("product", 1, productType);
        Product product1 = getProduct("product1", 2, productType);
        entityManager.persist(product);
        entityManager.persist(product1);
        Property property = getProperty("property");
        entityManager.persist(property);
        ProductProperty productProperty = getProductProperty(product, property);
        ProductProperty productProperty1 = getProductProperty(product1, property);
        repository.save(productProperty);
        repository.save(productProperty1);

        repository.delete(productProperty);
        entityManager.flush();

        Integer propertyCountActual = entityManager.find(Property.class, property.getId()).getCount();
        assertThat(propertyCountActual).isEqualTo(1);
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
