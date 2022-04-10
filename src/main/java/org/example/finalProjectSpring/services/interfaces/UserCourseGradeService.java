package org.example.finalProjectSpring.services.interfaces;

import org.example.finalProjectSpring.database.entity.UserCourseGrade;
import org.example.finalProjectSpring.model.enams.Grade;
import org.example.finalProjectSpring.model.responses.UserCourseGradeResponse;

import java.util.List;

public interface UserCourseGradeService {

    void createUserCourseGrade(String username, String courseId);

    void updateUserCourseGrades(String courseId, String[] studentsId, Grade[] grades);

    List<UserCourseGradeResponse> findAllByCourseId(String courseId);

    List<UserCourseGrade> findAllByUserId(String userId);

    void deleteCourseGrades(String courseId);
}
