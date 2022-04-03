package org.example.finalProjectSpring.service;

import org.example.finalProjectSpring.model.Role;
import org.example.finalProjectSpring.database.entity.User;

import java.util.List;

/**
 * Service class for {@link User}
 *
 * @author Kateryna Kravchenko
 * @version 1.0
 */

public interface UserService {

    User save(User user);

    User findByUsername(String username);

    User findByEmail(String email);

    List<User> findAllByRole(Role role);

    User findUserByFullName(String fullName);

    User findUserById(String id);
}
