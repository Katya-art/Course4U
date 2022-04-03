package org.example.finalProjectSpring.validator;

import org.example.finalProjectSpring.database.entity.User;
import org.example.finalProjectSpring.service.UserService;
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
public class UserValidator implements Validator {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fullName", "required");
        if (user.getFullName().length() < 6 || user.getFullName().length() > 32) {
            errors.rejectValue("fullName", "sizeUserFormFullName");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "required");
        if (user.getUsername().length() < 6 || user.getUsername().length() > 32) {
            errors.rejectValue("username", "sizeUserFormUsername");
        }

        if (userService.findByUsername(user.getUsername()) != null) {
            errors.rejectValue("username", "duplicateUserFormUsername");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "required");
        if (!user.getEmail().trim().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
            errors.rejectValue("email", "wrongEmail");
        }

        if (userService.findByEmail(user.getEmail()) != null) {
            errors.rejectValue("email", "duplicateUserFormEmail");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "required");
        if (user.getPassword().length() < 6 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "sizeUserFormPassword");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "required");
        if (!user.getConfirmPassword().equals(user.getPassword())) {
            errors.rejectValue("confirmPassword", "differentUserFormPassword");
        }
    }
}
