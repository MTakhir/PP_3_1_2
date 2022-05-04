package ru.kata.spring.boot_security.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDao {
    User findByEmail(String email);
    List<User> getUsers();
    void save(User user);
    User findUser(int id);
    void update(User user);
    void delete(int id);
}
