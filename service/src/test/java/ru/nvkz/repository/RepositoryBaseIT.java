package ru.nvkz.repository;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.nvkz.annotation.IT;

@IT
@Sql({
        "classpath:sql/data.sql"
})
public abstract class RepositoryBaseIT {

    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:13");

    static {
        postgres.start();
    }

    @Autowired
    protected EntityManager entityManager;

    @DynamicPropertySource
    public static void buildentityManagerFactory(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.datasource.url", postgres::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.username", postgres::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password", postgres::getPassword);
    }
}