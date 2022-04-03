package org.example.finalProjectSpring.database.repository;

import org.example.finalProjectSpring.model.Role;
import org.example.finalProjectSpring.database.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {
    User findByUsername(String username);
    User findByEmail(String email);
    List<User> findAllByRole(Role role);
    User findUserByFullName(String fullName);
    User findUserById(String id);
}
