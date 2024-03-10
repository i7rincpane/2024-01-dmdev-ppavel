package ru.nvkz.repository;

import org.springframework.stereotype.Repository;
import ru.nvkz.entity.User;

import javax.persistence.EntityManager;

@Repository
public class UserRepository extends RepositoryBase<Long, User> {

    public UserRepository(EntityManager entityManager) {
        super(User.class, entityManager);
    }
}
