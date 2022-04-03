package org.example.finalProjectSpring.database.repository;

import org.example.finalProjectSpring.database.entity.UserCourseGrade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCourseGradeRepository extends JpaRepository<UserCourseGrade, String> {
}
