package ru.nvkz.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;
import ru.nvkz.IntegrationTestBase;
import ru.nvkz.dto.BasketReadDto;
import ru.nvkz.dto.UserCreateEditDto;
import ru.nvkz.dto.UserReadDto;
import ru.nvkz.entity.Role;
import ru.nvkz.filter.UserFilter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RequiredArgsConstructor
public class UserServiceIT extends IntegrationTestBase {

    private static final Long USER_1 = 1L;
    private static final Long USER_5_WITHOUT_BASKET = 5L;

    private final UserService userService;
    private final BasketService basketService;

    @Test
    void findAll() {
        List<UserReadDto> result = userService.findAll(UserFilter.builder()
                .name("Ив")
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
        UserCreateEditDto userDto = getUserDto();

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
    void createBasketAfterCreateUser() {
        UserCreateEditDto userDto = getUserDto();

        UserReadDto actualResult = userService.create(userDto);

        Optional<BasketReadDto> maybeBasketForNewUser = basketService.findByUserId(actualResult.getId());
        assertTrue(maybeBasketForNewUser.isPresent());
        maybeBasketForNewUser.ifPresent(basketForUser -> assertAll(() -> {
            assertEquals(actualResult.getId(), basketForUser.getUserReadDto().getId());
            assertEquals(0, basketForUser.getCount());
            assertThat(BigDecimal.ZERO).isEqualByComparingTo(basketForUser.getSum());
        }));
    }

    @Test
    void update() {
        UserCreateEditDto userDto = getUserDto();

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
        assertTrue(userService.delete(USER_5_WITHOUT_BASKET));
        assertFalse(userService.delete(-124L));
    }

    private static UserCreateEditDto getUserDto() {
        return new UserCreateEditDto(
                "test@gmail.com",
                "password",
                LocalDate.now(),
                "Name",
                "Surname",
                "patronomic",
                Role.ADMIN,
                "77-77-77"
        );
    }

}