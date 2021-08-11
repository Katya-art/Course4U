package org.example.finalProjectSpring.service;

import org.example.finalProjectSpring.model.User;

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
}
