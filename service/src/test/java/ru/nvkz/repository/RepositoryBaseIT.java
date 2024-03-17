package ru.nvkz.repository;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.nvkz.annotation.IT;

@IT
public abstract class RepositoryBaseIT<T extends RepositoryBase> {

    @Autowired
    protected EntityManager entityManager;

    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:13");

    static {
        postgres.start();
    }

    @DynamicPropertySource
    public static void buildentityManagerFactory(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.datasource.url", postgres::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.username", postgres::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password", postgres::getPassword);
    }

    abstract void save();

    abstract void delete();

    abstract void update();

    abstract void findById();

    abstract void findAll();

}