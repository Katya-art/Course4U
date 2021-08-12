package org.example.finalProjectSpring.service;

import org.example.finalProjectSpring.model.Role;
import org.example.finalProjectSpring.model.User;

import java.util.List;

/**
 * Service class for {@link org.example.finalProjectSpring.model.User}
 *
 * @author Kateryna Kravchenko
 * @version 1.0
 */

public interface UserService {

    void save(User user, Long roleId);

    User findByUsername(String username);

    User findByEmail(String email);

    List<User> findAllByRole(Role role);

    User findUserByFullName(String fullName);
}
