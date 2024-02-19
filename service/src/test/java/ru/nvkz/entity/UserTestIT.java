package ru.nvkz.entity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UserTestIT extends BaseHibernateCrudTestIT {

    @Test
    @Override
    void create() {
        User user = getUser("test-name1");

        session.save(user);

        assertNotNull(user.getId());
    }

    @Test
    @Override
    void read() {
        User user1 = getUser("test-name1");
        User userExpected = getUser("test-name2");
        session.save(user1);
        session.save(userExpected);
        session.flush();
        session.clear();

        User userActual = session.get(User.class, userExpected.getId());

        assertThat(userActual.getPersonalInfo().getName()).isEqualTo(userExpected.getPersonalInfo().getName());
    }

    @Test
    @Override
    void update() {
        User userExpected = getUser("testName");
        session.save(userExpected);
        session.flush();
        session.clear();
        userExpected.getPersonalInfo().setName("updated");

        session.update(userExpected);
        session.flush();
        session.clear();

        User userActual = session.get(User.class, userExpected.getId());
        assertThat(userActual.getPersonalInfo().getName()).isEqualTo(userExpected.getPersonalInfo().getName());
    }

    @Test
    @Override
    void delete() {
        User user = getUser("testname");
        session.save(user);

        session.delete(user);
        session.flush();

        User userActual = session.get(User.class, user.getId());
        assertNull(userActual);
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
}
