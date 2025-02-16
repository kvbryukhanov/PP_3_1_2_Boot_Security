package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;

public interface RoleService {
    void save(Role role);

    Role findByName(String name);

    List<Role> getAllRoles();

    List<Role> findByIds(List<Long> roleIds);
}
