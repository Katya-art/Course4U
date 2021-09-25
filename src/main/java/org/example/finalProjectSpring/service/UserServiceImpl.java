package org.example.finalProjectSpring.service;

import org.example.finalProjectSpring.dao.RoleDao;
import org.example.finalProjectSpring.dao.UserDao;
import org.example.finalProjectSpring.model.Role;
import org.example.finalProjectSpring.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Implementation of {@link UserService} interface.
 *
 * @author Kateryna Kravchenko
 * @version 1.0
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User save(User user) {
        userDao.save(user);
        return user;
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public List<User> findAllByRole(Role role) {
        return userDao.findAllByRole(role);
    }

    @Override
    public User findUserByFullName(String fullName) {
        return userDao.findUserByFullName(fullName);
    }

    @Override
    public User findUserById(Long id) {
        return userDao.findUserById(id);
    }


}
