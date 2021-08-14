package org.example.finalProjectSpring.dao;

import org.example.finalProjectSpring.model.Course;
import org.example.finalProjectSpring.model.Mark;
import org.example.finalProjectSpring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Map;
import java.util.Set;

public interface CourseDao extends JpaRepository<Course, Long> {
    Course findCourseById(Long id);
    Set<Course> findAllByStudentsMarks(Map<User, Mark> studentsMarks);
}
