package org.example.finalProjectSpring.services.interfaces;

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
