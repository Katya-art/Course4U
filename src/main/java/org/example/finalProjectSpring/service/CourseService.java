package org.example.finalProjectSpring.service;

import org.example.finalProjectSpring.model.Course;
import org.example.finalProjectSpring.model.Mark;
import org.example.finalProjectSpring.model.User;

import java.util.Map;
import java.util.Set;

/**
 * Service class for {@link org.example.finalProjectSpring.model.Course}
 *
 * @author Kateryna Kravchenko
 * @version 1.0
 */

public interface CourseService {

    void save(Course course);

    void deleteCourseById(Long id);

    Course findCourseById(Long id);

    Set<Course> findAllByStudentsMarks(Map<User, Mark> studentsMarks);
}
