package ru.nvkz.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class OrderTestIT extends BaseHibernateCrudTestIT {

    @Test
    @Override
    void create() {
        User user = getUser("user");
        session.save(user);
        Order order = getOrder(user);

        session.save(order);

        assertNotNull(order.getId());
    }

    @Test
    @Override
    void read() {
        User user1 = getUser("user1");
        session.save(user1);
        User userExpected = getUser("userExpected");
        session.save(userExpected);
        Order order1 = getOrder(user1);
        session.save(order1);
        Order orderExpected = getOrder(userExpected);
        session.save(orderExpected);
        session.flush();
        session.clear();

        Order orderActual = session.get(Order.class, orderExpected.getId());

        assertThat(orderActual.getUser().getPersonalInfo().getName()).isEqualTo(userExpected.getPersonalInfo().getName());
    }

    @Test
    @Override
    void update() {
        User user = getUser("user");
        session.save(user);
        Order orderExpected = getOrder(user);
        session.save(orderExpected);
        session.flush();
        session.clear();
        orderExpected.setOrderStatus(OrderStatus.COMPLETED);

        session.update(orderExpected);
        session.flush();
        session.clear();

        Order orderActual = session.get(Order.class, orderExpected.getId());
        assertThat(orderActual.getOrderStatus()).isEqualTo(orderExpected.getOrderStatus());
    }

    @Test
    @Override
    void delete() {
        User user = getUser("user");
        session.save(user);
        Order order = getOrder(user);
        session.save(order);

        session.delete(order);
        session.flush();

        Order orderActual = session.get(Order.class, order.getId());
        assertNull(orderActual);
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
}
