package org.example.finalProjectSpring.service;

import org.example.finalProjectSpring.database.repository.UserRepository;
import org.example.finalProjectSpring.model.Role;
import org.example.finalProjectSpring.model.Status;
import org.example.finalProjectSpring.database.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
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

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User save(User user) {
        userRepository.save(user);
        return user;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findAllByRole(Role role) {
        return userRepository.findAllByRole(role);
    }

    @Override
    public User findUserByFullName(String fullName) {
        return userRepository.findUserByFullName(fullName);
    }

    @Override
    public User findUserById(String id) {
        return userRepository.findUserById(id);
    }

    @PostConstruct
    private void createDefaultUser() {
        User user = findByEmail("katakravchenko01@gmail.com");
        if (user == null) {
            user = User.builder()
                    .fullName("Kateryna Kravchenko")
                    .username("KaterynaKravchenko")
                    .email("katakravchenko01@gmail.com")
                    .password(bCryptPasswordEncoder.encode("qwerty"))
                    .role(Role.ROLE_ADMIN)
                    .status(Status.UNLOCK)
                    .build();
            save(user);
        }
    }

}
