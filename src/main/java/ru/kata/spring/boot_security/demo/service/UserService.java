package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

@Service
public interface UserService {
    List<User> findAll();

    User findById(Long id);

    User saveUser(User user);

    void deleteById(Long id);

    User findUserByName(String name);
}
