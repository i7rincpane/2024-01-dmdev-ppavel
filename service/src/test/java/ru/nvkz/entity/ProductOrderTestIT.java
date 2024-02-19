package ru.nvkz.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ProductOrderTestIT extends BaseHibernateCrudTestIT {

    @Test
    @Override
    void create() {
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

        assertNotNull(productOrder.getId());
    }

    @Test
    @Override
    void read() {
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

        ProductOrder productOrderActual = session.get(ProductOrder.class, productOrderExpected.getId());

        assertThat(productOrderActual.getOrder().getUser().getPersonalInfo().getName()).isEqualTo(userExpected.getPersonalInfo().getName());
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

        session.update(productOrderExpected);
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

        session.delete(productOrder);
        session.flush();

        ProductOrder productOrderActual = session.get(ProductOrder.class, productOrder.getId());
        assertNull(productOrderActual);
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
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .orderStatus(OrderStatus.DRAFT)
                .build();
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

    private ProductType getProductType(String name) {
        return ProductType.builder()
                .name(name)
                .build();
    }
}
