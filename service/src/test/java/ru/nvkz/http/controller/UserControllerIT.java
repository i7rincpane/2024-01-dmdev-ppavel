package ru.nvkz.http.controller;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.web.servlet.MockMvc;
import ru.nvkz.IntegrationTestBase;
import ru.nvkz.dto.UserCreateEditDto;
import ru.nvkz.entity.Role;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class UserControllerIT extends IntegrationTestBase {

    private final MockMvc mockMvc;

    @BeforeEach
    void init() {
        List<GrantedAuthority> authorities = Arrays.asList(Role.ADMIN, Role.USER);
        User testUser = new User("test@mail.ru", "123", authorities);
        TestingAuthenticationToken testingAuthenticationToken = new TestingAuthenticationToken(testUser, testUser.getAuthorities(), authorities);

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(testingAuthenticationToken);
        SecurityContextHolder.setContext(securityContext);
    }


    @Test
    void findAll() throws Exception {

        mockMvc.perform(get("/users"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("user/users"))
                .andExpect(model().attributeExists("users"));
    }

    @Test
    void create() throws Exception {
        mockMvc.perform(post("/users")
                        .param(UserCreateEditDto.Fields.email, "test@gmail.com")
                        .param(UserCreateEditDto.Fields.name, "Test")
                        .param(UserCreateEditDto.Fields.password, "TestPassword")
                        .param(UserCreateEditDto.Fields.surname, "TestTest")
                        .param(UserCreateEditDto.Fields.patronimic, "TestTestTest")
                        .param(UserCreateEditDto.Fields.role, "ADMIN")
                        .param(UserCreateEditDto.Fields.telephone, "77-77-77")
                        .param(UserCreateEditDto.Fields.birthDate, "2000-01-01")
                )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrlPattern("/users/{\\d+}")
                );
    }
}