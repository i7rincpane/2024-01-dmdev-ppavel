package ru.nvkz.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import ru.nvkz.entity.Order;
import ru.nvkz.entity.OrderStatus;
import ru.nvkz.entity.PersonalInfo;
import ru.nvkz.entity.User;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@RequiredArgsConstructor
public class OrderRepositoryIT extends RepositoryBaseIT {

    private final OrderRepository repository;

    @Test
    @Override
    void save() {
        User user = getUser("user");
        entityManager.persist(user);
        Order order = getOrder(user);

        repository.save(order);

        assertNotNull(order.getId());
    }

    @Test
    @Override
    void findById() {
        User user = getUser("user");
        entityManager.persist(user);
        User userExpected = getUser("userExpected");
        entityManager.persist(userExpected);
        Order order1 = getOrder(user);
        entityManager.persist(order1);
        Order orderExpected = getOrder(userExpected);
        entityManager.persist(orderExpected);
        entityManager.flush();
        entityManager.clear();

        Optional<Order> orderActual = repository.findById(orderExpected.getId());

        assertThat(orderActual).isPresent();
        assertThat(orderActual.get().getUser().getPersonalInfo().getName()).isEqualTo(userExpected.getPersonalInfo().getName());
    }

    @Test
    @Override
    void update() {
        User user = getUser("user");
        entityManager.persist(user);
        Order orderExpected = getOrder(user);
        entityManager.persist(orderExpected);
        entityManager.flush();
        entityManager.clear();
        orderExpected.setOrderStatus(OrderStatus.COMPLETED);

        repository.update(orderExpected);
        entityManager.flush();
        entityManager.clear();

        Order orderActual = entityManager.find(Order.class, orderExpected.getId());
        assertThat(orderActual.getOrderStatus()).isEqualTo(orderExpected.getOrderStatus());
    }

    @Override
    void findAll() {
        User user = getUser("user");
        User user1 = getUser("user1");
        User user2 = getUser("user2");
        Order order = getOrder(user);
        Order order1 = getOrder(user1);
        Order order2 = getOrder(user2);
        entityManager.persist(order);
        entityManager.persist(order1);
        entityManager.persist(order2);
        entityManager.flush();
        entityManager.clear();

        List<Order> orderActualBatch = repository.findAll();

        assertThat(orderActualBatch).hasSize(3);
        List<Long> orderActualIdBatch = orderActualBatch.stream()
                .map(Order::getId)
                .toList();
        assertThat(orderActualIdBatch).contains(order.getId(),
                order1.getId(),
                order2.getId());
    }

    @Test
    @Override
    void delete() {
        User user = getUser("user");
        entityManager.persist(user);
        Order order = getOrder(user);
        entityManager.persist(order);

        repository.delete(order);

        Order orderActual = entityManager.find(Order.class, order.getId());
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
