package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;
import java.util.Set;

@Service
public interface RoleService {
    List<Role> getAllRoles();

    Set<Role> getSetRoles(String[] role);
}
