package ru.nvkz.repository;

import org.hibernate.Session;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.nvkz.config.ApplicationTestConfiguration;

public abstract class RepositoryBaseIT<T extends RepositoryBase> {

    private static AnnotationConfigApplicationContext context;

    protected final Session session;
    protected final T repository;

    public RepositoryBaseIT(Class<T> clazz) {
        context = new AnnotationConfigApplicationContext(ApplicationTestConfiguration.class);
        repository = context.getBean(clazz);
        session = (Session) repository.getEntityManager();
    }

    @BeforeEach
    void beginTransaction() {
        session.beginTransaction();
    }

    @AfterEach
    void closeSession() {
        session.getTransaction().rollback();
        session.close();
    }

    @AfterAll
    static void closeContext() {
        context.close();
    }

    abstract void save();

    abstract void delete();

    abstract void update();

    abstract void findById();

    abstract void findAll();

}