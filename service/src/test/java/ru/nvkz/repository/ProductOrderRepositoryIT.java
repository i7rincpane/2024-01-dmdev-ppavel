package ru.nvkz.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import ru.nvkz.entity.Order;
import ru.nvkz.entity.OrderStatus;
import ru.nvkz.entity.PersonalInfo;
import ru.nvkz.entity.Product;
import ru.nvkz.entity.ProductOrder;
import ru.nvkz.entity.ProductType;
import ru.nvkz.entity.User;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@RequiredArgsConstructor
public class ProductOrderRepositoryIT extends RepositoryBaseIT {

    private final ProductOrderRepository repository;

    @Test
    @Override
    void save() {
        ProductType productType = getProductType("productType");
        entityManager.persist(productType);
        Product product = getProduct("product", productType);
        entityManager.persist(product);
        User user = getUser("user1");
        entityManager.persist(user);
        Order order = getOrder(user);
        entityManager.persist(order);
        ProductOrder productOrder = getProductOrder(product, order);

        repository.save(productOrder);

        assertNotNull(productOrder.getId());
    }

    @Test
    @Override
    void findById() {
        ProductType productType = getProductType("productType");
        entityManager.persist(productType);
        Product product = getProduct("product", productType);
        entityManager.persist(product);
        User user = getUser("user1");
        entityManager.persist(user);
        Order order = getOrder(user);
        entityManager.persist(order);
        ProductOrder productOrder = getProductOrder(product, order);
        entityManager.persist(productOrder);
        User userExpected = getUser("userExpected");
        entityManager.persist(user);
        Order orderExpected = getOrder(user);
        entityManager.persist(orderExpected);
        ProductOrder productOrderExpected = getProductOrder(product, orderExpected);
        entityManager.persist(productOrderExpected);
        entityManager.flush();
        entityManager.clear();

        Optional<ProductOrder> productOrderActual = repository.findById(productOrderExpected.getId());

        assertThat(productOrderActual).isPresent();
        assertThat(productOrderActual.get().getOrder().getUser().getPersonalInfo().getName()).isEqualTo(userExpected.getPersonalInfo().getName());
    }

    @Test
    @Override
    void update() {
        ProductType productType = getProductType("productType");
        entityManager.persist(productType);
        Product product = getProduct("product", productType);
        entityManager.persist(product);
        User user = getUser("user1");
        entityManager.persist(user);
        Order order = getOrder(user);
        entityManager.persist(order);
        ProductOrder productOrderExpected = getProductOrder(product, order);
        entityManager.persist(productOrderExpected);
        entityManager.flush();
        entityManager.clear();
        productOrderExpected.setCount(11);

        repository.update(productOrderExpected);
        entityManager.flush();
        entityManager.clear();

        ProductOrder productOrderActual = entityManager.find(ProductOrder.class, productOrderExpected.getId());
        assertThat(productOrderActual.getCount()).isEqualTo(productOrderExpected.getCount());
    }


    @Test
    @Override
    void delete() {
        ProductType productType = getProductType("productType");
        entityManager.persist(productType);
        Product product = getProduct("product", productType);
        entityManager.persist(product);
        User user = getUser("user1");
        entityManager.persist(user);
        Order order = getOrder(user);
        entityManager.persist(order);
        ProductOrder productOrder = getProductOrder(product, order);
        entityManager.persist(productOrder);

        repository.delete(productOrder);
        entityManager.flush();

        ProductOrder productOrderActual = entityManager.find(ProductOrder.class, productOrder.getId());
        assertNull(productOrderActual);
    }

    @Override
    void findAll() {
        ProductType productType = getProductType("productType");
        entityManager.persist(productType);
        Product product = getProduct("product", productType);
        Product product1 = getProduct("product1", productType);
        Product product2 = getProduct("product2", productType);
        entityManager.persist(product);
        entityManager.persist(product1);
        entityManager.persist(product2);
        User user = getUser("user");
        entityManager.persist(user);
        Order order = getOrder(user);
        entityManager.persist(order);
        ProductOrder productOrder = getProductOrder(product, order);
        ProductOrder productOrder1 = getProductOrder(product1, order);
        ProductOrder productOrder2 = getProductOrder(product2, order);
        entityManager.persist(productOrder);
        entityManager.persist(productOrder1);
        entityManager.persist(productOrder2);
        entityManager.flush();
        entityManager.clear();

        List<ProductOrder> productOrderActualBatch = repository.findAll();

        assertThat(productOrderActualBatch).hasSize(3);
        List<Long> productOrderIdActualBatch = productOrderActualBatch.stream()
                .map(ProductOrder::getId)
                .toList();
        assertThat(productOrderIdActualBatch).contains(productOrder.getId(),
                productOrder1.getId(),
                productOrder2.getId());
    }

    private ProductOrder getProductOrder(Product product, Order order) {
        return ProductOrder.builder()
                .product(product)
                .order(order)
                .count(1)
                .build();

    }

    private User getUser(String name) {
        return User.builder()
                .email(name + "@mail.ru")
                .password("111")
                .personalInfo(PersonalInfo.builder()
                        .name("name")
                        .surname("surname")
                        .patronimic("patronimic")
                        .telephone("8-888-888-8888")
                        .build())
                .build();
    }

    private Order getOrder(User user) {
        return Order.builder()
                .sum(new BigDecimal(1.1))
                .user(user)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .orderStatus(OrderStatus.DRAFT)
                .build();
    }

    private Product getProduct(String name, ProductType productType) {
        return Product.builder()
                .code(1)
                .name(name)
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
