package ru.kata.spring.boot_security.demo.service;


import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;
import java.util.List;

public interface UserService {
    List<User> getUsers();
    void save(User user);
    User findUser(int id);
    User findByEmail(String email);
    void update(User user, int id);
    void delete(int id);
}
