package ru.nvkz.dao;

import org.junit.jupiter.api.BeforeEach;
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

public class ProductOrderRepositoryTestIT extends RepositoryBaseTestIT<ProductOrderRepository> {

    @BeforeEach
    @Override
    void initRepository() {
        repository = new ProductOrderRepository(session);
    }

    @Test
    @Override
    void save() {
        ProductType productType = getProductType("productType");
        session.save(productType);
        Product product = getProduct("product", productType);
        session.save(product);
        User user = getUser("user1");
        session.save(user);
        Order order = getOrder(user);
        session.save(order);
        ProductOrder productOrder = getProductOrder(product, order);

        repository.save(productOrder);

        assertNotNull(productOrder.getId());
    }

    @Test
    @Override
    void findById() {
        ProductType productType = getProductType("productType");
        session.save(productType);
        Product product = getProduct("product", productType);
        session.save(product);
        User user = getUser("user1");
        session.save(user);
        Order order = getOrder(user);
        session.save(order);
        ProductOrder productOrder = getProductOrder(product, order);
        session.save(productOrder);
        User userExpected = getUser("userExpected");
        session.save(user);
        Order orderExpected = getOrder(user);
        session.save(orderExpected);
        ProductOrder productOrderExpected = getProductOrder(product, orderExpected);
        session.save(productOrderExpected);
        session.flush();
        session.clear();

        Optional<ProductOrder> productOrderActual = repository.findById(productOrderExpected.getId());

        assertThat(productOrderActual).isPresent();
        assertThat(productOrderActual.get().getOrder().getUser().getPersonalInfo().getName()).isEqualTo(userExpected.getPersonalInfo().getName());
    }

    @Test
    @Override
    void update() {
        ProductType productType = getProductType("productType");
        session.save(productType);
        Product product = getProduct("product", productType);
        session.save(product);
        User user = getUser("user1");
        session.save(user);
        Order order = getOrder(user);
        session.save(order);
        ProductOrder productOrderExpected = getProductOrder(product, order);
        session.save(productOrderExpected);
        session.flush();
        session.clear();
        productOrderExpected.setCount(11);

        repository.update(productOrderExpected);
        session.flush();
        session.clear();

        ProductOrder productOrderActual = session.get(ProductOrder.class, productOrderExpected.getId());
        assertThat(productOrderActual.getCount()).isEqualTo(productOrderExpected.getCount());
    }


    @Test
    @Override
    void delete() {
        ProductType productType = getProductType("productType");
        session.save(productType);
        Product product = getProduct("product", productType);
        session.save(product);
        User user = getUser("user1");
        session.save(user);
        Order order = getOrder(user);
        session.save(order);
        ProductOrder productOrder = getProductOrder(product, order);
        session.save(productOrder);

        repository.delete(productOrder.getId());
        session.flush();

        ProductOrder productOrderActual = session.get(ProductOrder.class, productOrder.getId());
        assertNull(productOrderActual);
    }

    @Override
    void findAll() {
        ProductType productType = getProductType("productType");
        session.save(productType);
        Product product = getProduct("product", productType);
        Product product1 = getProduct("product1", productType);
        Product product2 = getProduct("product2", productType);
        session.save(product);
        session.save(product1);
        session.save(product2);
        User user = getUser("user");
        session.save(user);
        Order order = getOrder(user);
        session.save(order);
        ProductOrder productOrder = getProductOrder(product, order);
        ProductOrder productOrder1 = getProductOrder(product1, order);
        ProductOrder productOrder2 = getProductOrder(product2, order);
        session.save(productOrder);
        session.save(productOrder1);
        session.save(productOrder2);
        session.flush();
        session.clear();

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
