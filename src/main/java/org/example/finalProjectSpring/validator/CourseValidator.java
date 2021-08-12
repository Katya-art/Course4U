package org.example.finalProjectSpring.validator;

import org.example.finalProjectSpring.model.Course;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validator for {@link org.example.finalProjectSpring.model.Course} class,
 * implements {@link Validator} interface.
 *
 * @author Kateryna Kravchenko
 * @version 1.0
 */

@Component
public class CourseValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return Course.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Course course = (Course) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required");
        if (course.getName().length() < 6 || course.getName().length() > 255) {
            errors.rejectValue("name", "sizeCourseFormName");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "theme", "required");
        if (course.getTheme().length() < 6 || course.getTheme().length() > 255) {
            errors.rejectValue("theme", "sizeCourseFormTheme");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "duration", "required");
        if (!course.getDuration().trim().matches("\\d")) {
            errors.rejectValue("duration", "wrongDuration");
        }

        if (course.getTeacherName().equals("NONE")) {
            errors.rejectValue("teacherName", "required");
        }
    }
}
