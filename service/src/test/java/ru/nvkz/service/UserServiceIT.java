package ru.nvkz.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;
import ru.nvkz.IntegrationTestBase;
import ru.nvkz.dto.UserCreateEditDto;
import ru.nvkz.dto.UserReadDto;
import ru.nvkz.entity.Role;
import ru.nvkz.filter.UserFilter;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RequiredArgsConstructor
public class UserServiceIT extends IntegrationTestBase {

    private static final Long USER_1 = 1L;

    private final UserService userService;

    @Test
    void findAll() {
        List<UserReadDto> result = userService.findAll(UserFilter.builder()
                .name("Им")
                .build(), Pageable.ofSize(20)).getContent();
        assertThat(result).hasSize(1);
    }

    @Test
    void findById() {
        Optional<UserReadDto> mabyUser = userService.findById(USER_1);
        assertTrue(mabyUser.isPresent());
        mabyUser.ifPresent(user -> assertEquals("test@mail.ru", user.getEmail()));
    }

    @Test
    void create() {
        UserCreateEditDto userDto = new UserCreateEditDto(
                "test@gmail.com",
                "password",
                LocalDate.now(),
                "Name",
                "Surname",
                "patronomic",
                Role.ADMIN,
                "77-77-77"
        );

        UserReadDto actualResult = userService.create(userDto);

        assertEquals(userDto.getEmail(), actualResult.getEmail());
        assertEquals(userDto.getName(), actualResult.getName());
        assertEquals(userDto.getSurname(), actualResult.getSurname());
        assertEquals(userDto.getPatronimic(), actualResult.getPatronimic());
        assertEquals(userDto.getBirthDate(), actualResult.getBirthDate());
        assertEquals(userDto.getTelephone(), actualResult.getTelephone());
        assertSame(userDto.getRole(), actualResult.getRole());
    }

    @Test
    void update() {
        UserCreateEditDto userDto = new UserCreateEditDto(
                "update@gmail.com",
                "password",
                LocalDate.now(),
                "Name",
                "Surname",
                "patronomic",
                Role.ADMIN,
                "77-77-77"
        );

        Optional<UserReadDto> actualResult = userService.update(USER_1, userDto);
        assertTrue(actualResult.isPresent());
        actualResult.ifPresent(user -> {
            assertEquals(userDto.getEmail(), user.getEmail());
            assertEquals(userDto.getName(), user.getName());
            assertEquals(userDto.getSurname(), user.getSurname());
            assertEquals(userDto.getPatronimic(), user.getPatronimic());
            assertEquals(userDto.getBirthDate(), user.getBirthDate());
            assertEquals(userDto.getTelephone(), user.getTelephone());
            assertSame(userDto.getRole(), user.getRole());
        });

    }

    @Test
    void delete() {
        assertTrue(userService.delete(USER_1));
        assertFalse(userService.delete(-124L));
    }

}