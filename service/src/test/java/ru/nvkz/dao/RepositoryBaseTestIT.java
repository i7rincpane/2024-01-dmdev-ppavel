package ru.nvkz.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import ru.nvkz.util.HibernateTestUtil;

public abstract class RepositoryBaseTestIT<T> {

    private static SessionFactory sessionFactory;
    protected Session session;
    protected T repository;

    @BeforeAll
    static void initSessionFactory() {
        sessionFactory = HibernateTestUtil.buildSessionFactory();
    }

    @BeforeEach
    void openSession() {
        session = sessionFactory.openSession();
        session.beginTransaction();
    }

    @AfterEach
    void closeSession() {
        session.getTransaction().rollback();
        session.close();
    }

    @AfterAll
    static void closeSessionFactory() {
        sessionFactory.close();
    }

    abstract void save();

    abstract void delete();

    abstract void update();

    abstract void findById();

    abstract void findAll();

    abstract void initRepository();
}