package org.example.finalProjectSpring.services.interfaces;

import org.example.finalProjectSpring.database.entity.Course;
import org.example.finalProjectSpring.database.entity.User;
import org.example.finalProjectSpring.model.dto.CourseDTO;
import org.example.finalProjectSpring.model.enams.Condition;
import org.example.finalProjectSpring.model.responses.CourseResponse;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Service class for {@link Course}
 *
 * @author Kateryna Kravchenko
 * @version 1.0
 */

public interface CourseService {

    void createCourse(CourseDTO courseDTO);

    void updateCourse(String courseId, CourseDTO courseDTO);

    void deleteCourseById(String courseId);

    Course findCourseById(String courseId);

    CourseDTO getCourseDTO(String courseId);

    void changeCourseCondition(String courseId, Condition condition);

    Page<Course> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);

    Page<Course> findPaginatedByTeacher(int pageNo, int pageSize, String sortField, String sortDirection, User teacher);

    Page<Course> findPaginatedByTheme(int pageNo, int pageSize, String sortField, String sortDirection, String theme);

    Page<Course> findPaginatedByTeacherAndTheme(int pageNo, int pageSize, String sortField, String sortDirection, User teacher, String theme);

    List<CourseResponse> findAllByUsernameAndCondition(String username, String condition);

}
