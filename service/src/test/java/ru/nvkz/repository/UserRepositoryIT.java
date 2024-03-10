package ru.nvkz.repository;

import org.junit.jupiter.api.Test;
import ru.nvkz.entity.PersonalInfo;
import ru.nvkz.entity.User;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UserRepositoryIT extends RepositoryBaseIT<UserRepository> {

    public UserRepositoryIT() {
        super(UserRepository.class);
    }

    @Test
    @Override
    void save() {
        User user = getUser("test-name1");

        repository.save(user);

        assertNotNull(user.getId());
    }

    @Test
    @Override
    void findById() {
        User user1 = getUser("test-name1");
        User userExpected = getUser("test-name2");
        session.save(user1);
        session.save(userExpected);
        session.flush();
        session.clear();

        Optional<User> userActual = repository.findById(userExpected.getId());

        assertThat(userActual.get().getPersonalInfo().getName()).isEqualTo(userExpected.getPersonalInfo().getName());
    }

    @Test
    @Override
    void update() {
        User userExpected = getUser("testName");
        session.save(userExpected);
        session.flush();
        session.clear();
        userExpected.getPersonalInfo().setName("updated");

        repository.update(userExpected);
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

        repository.delete(user);

        User userActual = session.get(User.class, user.getId());
        assertNull(userActual);
    }

    @Override
    void findAll() {
        User user = getUser("test-name1");
        User user1 = getUser("test-name2");
        User user2 = getUser("test-name3");
        session.save(user);
        session.save(user1);
        session.save(user2);
        session.flush();
        session.clear();

        List<User> userActualBatch = repository.findAll();

        assertThat(userActualBatch).hasSize(3);
        List<Long> userIdBatch = userActualBatch.stream()
                .map(User::getId)
                .toList();
        assertThat(userIdBatch).contains(user.getId(),
                user1.getId(),
                user2.getId());
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
