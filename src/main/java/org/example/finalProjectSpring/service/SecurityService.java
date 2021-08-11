package org.example.finalProjectSpring.service;

/**
 * Service for Security.
 *
 * @author Kateryna Kravchenko
 * @version 1.0
 */

public interface SecurityService {

    String findLoggedInUsername();

    void autoLogin(String username, String password);
}
