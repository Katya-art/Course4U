package org.example.finalProjectSpring.database.repository;

import org.example.finalProjectSpring.model.enams.Role;
import org.example.finalProjectSpring.database.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findUserById(String id);

    Optional<User> findUserByFullName(String fullName);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    List<User> findAllByRole(Role role);

}
