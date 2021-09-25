package org.example.finalProjectSpring.dao;

import org.example.finalProjectSpring.model.Condition;
import org.example.finalProjectSpring.model.Course;
import org.example.finalProjectSpring.model.Mark;
import org.example.finalProjectSpring.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Map;
import java.util.Set;

public interface CourseDao extends JpaRepository<Course, Long> {

    Course findByName(String name);

    Course findCourseById(Long id);

    Page<Course> findAllByTeacher(User teacher, Pageable pageable);

    Page<Course> findAllByTheme(String theme, Pageable pageable);

    Page<Course> findAllByTeacherAndTheme(User teacher, String theme, Pageable pageable);
}
