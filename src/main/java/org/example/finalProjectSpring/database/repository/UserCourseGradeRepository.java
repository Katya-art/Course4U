package org.example.finalProjectSpring.database.repository;

import org.example.finalProjectSpring.database.entity.UserCourseGrade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserCourseGradeRepository extends JpaRepository<UserCourseGrade, String> {

    Optional<UserCourseGrade> findByUserIdAndCourseId(String userId, String courseId);

    List<UserCourseGrade> findAllByCourseId(String courseId);

    List<UserCourseGrade> findAllByUserId(String userId);

}
