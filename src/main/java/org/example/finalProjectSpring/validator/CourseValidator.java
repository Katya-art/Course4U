package org.example.finalProjectSpring.validator;

import org.example.finalProjectSpring.database.entity.Course;
import org.example.finalProjectSpring.model.dto.CourseDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validator for {@link Course} class,
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
        CourseDTO courseDTO = (CourseDTO) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "courseName", "required");
        if (courseDTO.getCourseTheme().length() < 6 || courseDTO.getCourseName().length() > 255) {
            errors.rejectValue("courseName", "sizeCourseFormName");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "courseTheme", "required");
        if (courseDTO.getCourseTheme().length() < 6 || courseDTO.getCourseTheme().length() > 255) {
            errors.rejectValue("courseTheme", "sizeCourseFormTheme");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "duration", "required");
        if (courseDTO.getDuration() <= 0) {
            errors.rejectValue("duration", "wrongDuration");
        }

        if (courseDTO.getTeacherName().equals("NONE")) {
            errors.rejectValue("teacherName", "required");
        }
    }
}
