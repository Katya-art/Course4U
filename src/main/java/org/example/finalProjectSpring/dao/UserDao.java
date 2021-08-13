package org.example.finalProjectSpring.dao;

import org.example.finalProjectSpring.model.Role;
import org.example.finalProjectSpring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDao extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);
    List<User> findAllByRole(Role role);
    User findUserByFullName(String fullName);
    User findUserById(Long id);
}
