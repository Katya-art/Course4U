package org.example.finalProjectSpring.dao;

import org.example.finalProjectSpring.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseDao extends JpaRepository<Course, Long> {
}
