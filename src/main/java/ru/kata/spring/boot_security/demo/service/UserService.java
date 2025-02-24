package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService extends UserDetailsService {
    List<User> index();

    User show(int id);

    void save(User user, List<Long> roleIds);

    void save(User user);

    void update(int id, ru.kata.spring.boot_security.demo.model.User user, List<Long> roleIds);

    void delete(int id);

    Optional<User> findUserByUsername(String username);

    String encodePassword(String password);

    Set<Role> getUserRoles(int id);
}