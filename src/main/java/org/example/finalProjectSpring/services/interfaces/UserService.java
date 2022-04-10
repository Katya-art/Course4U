package org.example.finalProjectSpring.services.interfaces;

import org.example.finalProjectSpring.model.dto.UserDTO;
import org.example.finalProjectSpring.model.enams.Role;
import org.example.finalProjectSpring.database.entity.User;

import java.util.List;

/**
 * Service class for {@link User}
 *
 * @author Kateryna Kravchenko
 * @version 1.0
 */

public interface UserService {

    User createStudent(UserDTO userDTO);

    User createTeacher(UserDTO userDTO);

    User findUserById(String id);

    User findUserByFullName(String fullName);

    User findUserByUsername(String username);

    User findByEmail(String email);

    List<User> findAllByRole(Role role);

    void changeUserStatus(String userId, String status);

}
