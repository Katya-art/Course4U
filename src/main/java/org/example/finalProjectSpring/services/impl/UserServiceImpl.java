package org.example.finalProjectSpring.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.finalProjectSpring.database.repository.UserRepository;
import org.example.finalProjectSpring.exception.InternalViolationException;
import org.example.finalProjectSpring.exception.InternalViolationType;
import org.example.finalProjectSpring.model.dto.UserDTO;
import org.example.finalProjectSpring.model.enams.Role;
import org.example.finalProjectSpring.model.enams.Status;
import org.example.finalProjectSpring.database.entity.User;
import org.example.finalProjectSpring.services.interfaces.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Implementation of {@link UserService} interface.
 *
 * @author Kateryna Kravchenko
 * @version 1.0
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User createStudent(UserDTO userDTO) {
        User user = User.builder()
                .fullName(userDTO.getFullName())
                .username(userDTO.getUsername())
                .email(userDTO.getEmail())
                .password(bCryptPasswordEncoder.encode(userDTO.getPassword()))
                .role(Role.ROLE_STUDENT)
                .build();
        return userRepository.save(user);
    }

    @Override
    public User createTeacher(UserDTO userDTO) {
        User user = User.builder()
                .fullName(userDTO.getFullName())
                .username(userDTO.getUsername())
                .email(userDTO.getEmail())
                .password(bCryptPasswordEncoder.encode(userDTO.getPassword()))
                .role(Role.ROLE_TEACHER)
                .build();
        return userRepository.save(user);
    }

    @Override
    public User findUserById(String id) {
        return userRepository.findUserById(id).orElseThrow(() ->
                new InternalViolationException(InternalViolationType.USER_NOT_FOUND_EXCEPTION));
    }

    @Override
    public User findUserByFullName(String fullName) {
        return userRepository.findUserByFullName(fullName).orElseThrow(() ->
                new InternalViolationException(InternalViolationType.USER_NOT_FOUND_EXCEPTION));
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() ->
                new InternalViolationException(InternalViolationType.USER_NOT_FOUND_EXCEPTION));
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() ->
                new InternalViolationException(InternalViolationType.USER_NOT_FOUND_EXCEPTION));
    }

    @Override
    public List<User> findAllByRole(Role role) {
        return userRepository.findAllByRole(role);
    }

    @Override
    public void changeUserStatus(String userId, String status) {
        User user = findUserById(userId);
        user.setStatus(Status.valueOf(status));
        userRepository.save(user);
    }

    @PostConstruct
    private void createDefaultUser() {
        if (!userRepository.findByEmail("katakravchenko01@gmail.com").isPresent()) {
            User user = User.builder()
                    .fullName("Kateryna Kravchenko")
                    .username("KaterynaKravchenko")
                    .email("katakravchenko01@gmail.com")
                    .password(bCryptPasswordEncoder.encode("qwerty"))
                    .role(Role.ROLE_ADMIN)
                    .build();
            userRepository.save(user);
        }
    }

}
