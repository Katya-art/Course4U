package org.example.finalProjectSpring.validator;

import lombok.RequiredArgsConstructor;
import org.example.finalProjectSpring.database.entity.User;
import org.example.finalProjectSpring.database.repository.UserRepository;
import org.example.finalProjectSpring.model.dto.UserDTO;
import org.example.finalProjectSpring.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validator for {@link User} class,
 * implements {@link Validator} interface.
 *
 * @author Kateryna Kravchenko
 * @version 1.0
 */

@Component
@RequiredArgsConstructor
public class UserValidator implements Validator {

    private final UserRepository userRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserDTO userDTO = (UserDTO) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fullName", "required");
        if (userDTO.getFullName().length() < 6 || userDTO.getFullName().length() > 32) {
            errors.rejectValue("fullName", "sizeUserFormFullName");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "required");
        if (userDTO.getUsername().length() < 6 || userDTO.getUsername().length() > 32) {
            errors.rejectValue("username", "sizeUserFormUsername");
        }

        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            errors.rejectValue("username", "duplicateUserFormUsername");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "required");
        if (!userDTO.getEmail().trim().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
            errors.rejectValue("email", "wrongEmail");
        }

        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            errors.rejectValue("email", "duplicateUserFormEmail");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "required");
        if (userDTO.getPassword().length() < 6 || userDTO.getPassword().length() > 32) {
            errors.rejectValue("password", "sizeUserFormPassword");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "required");
        if (!userDTO.getConfirmPassword().equals(userDTO.getPassword())) {
            errors.rejectValue("confirmPassword", "differentUserFormPassword");
        }
    }
}
