package ru.nvkz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nvkz.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
