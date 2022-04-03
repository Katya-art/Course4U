package org.example.finalProjectSpring.service;

import org.example.finalProjectSpring.database.entity.Course;
import org.example.finalProjectSpring.database.entity.User;
import org.springframework.data.domain.Page;

/**
 * Service class for {@link Course}
 *
 * @author Kateryna Kravchenko
 * @version 1.0
 */

public interface CourseService {

    void save(Course course);

    void deleteCourseById(String id);

    Course findCourseById(String id);

    Page<Course> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);

    Page<Course> findPaginatedByTeacher(int pageNo, int pageSize, String sortField, String sortDirection, User teacher);

    Page<Course> findPaginatedByTheme(int pageNo, int pageSize, String sortField, String sortDirection, String theme);

    Page<Course> findPaginatedByTeacherAndTheme(int pageNo, int pageSize, String sortField, String sortDirection, User teacher, String theme);
}
