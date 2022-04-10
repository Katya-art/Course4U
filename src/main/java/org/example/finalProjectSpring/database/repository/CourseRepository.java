package org.example.finalProjectSpring.database.repository;

import org.example.finalProjectSpring.database.entity.Course;
import org.example.finalProjectSpring.database.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, String> {

    Optional<Course> findCourseById(String id);

    Page<Course> findAllByTeacher(User teacher, Pageable pageable);

    Page<Course> findAllByTheme(String theme, Pageable pageable);

    Page<Course> findAllByTeacherAndTheme(User teacher, String theme, Pageable pageable);
}
