package org.example.finalProjectSpring.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.finalProjectSpring.database.entity.Course;
import org.example.finalProjectSpring.database.entity.User;
import org.example.finalProjectSpring.database.entity.UserCourseGrade;
import org.example.finalProjectSpring.database.repository.CourseRepository;
import org.example.finalProjectSpring.database.repository.UserCourseGradeRepository;
import org.example.finalProjectSpring.exception.InternalViolationException;
import org.example.finalProjectSpring.exception.InternalViolationType;
import org.example.finalProjectSpring.model.enams.Grade;
import org.example.finalProjectSpring.model.responses.UserCourseGradeResponse;
import org.example.finalProjectSpring.services.interfaces.UserCourseGradeService;
import org.example.finalProjectSpring.services.interfaces.UserService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserCourseGradeServiceImpl implements UserCourseGradeService {

    private final UserCourseGradeRepository userCourseGradeRepository;
    private final CourseRepository courseRepository;
    private final UserService userService;

    @Override
    public void createUserCourseGrade(String username, String courseId) {
        User user = userService.findUserByUsername(username);
        Course course = courseRepository.findCourseById(courseId).orElseThrow(() ->
                new InternalViolationException(InternalViolationType.COURSE_NOT_FOUND_EXCEPTION));
        if (userCourseGradeRepository.findByUserIdAndCourseId(user.getId(), courseId).isPresent()) {
            throw new InternalViolationException(InternalViolationType.USER_COURSE_GRADE_ALREADY_EXCEPTION);
        }
        UserCourseGrade userCourseGrade = UserCourseGrade.builder()
                .userId(user.getId())
                .courseId(courseId)
                .grade(Grade.Fx)
                .build();
        userCourseGradeRepository.save(userCourseGrade);
        course.setNumberOfStudents(course.getNumberOfStudents() + 1);
        courseRepository.save(course);
    }

    @Override
    public void updateUserCourseGrades(String courseId, String[] studentsId, Grade[] grades) {
        deleteCourseGrades(courseId);
        Set<UserCourseGrade> userCourseGradesSet = new HashSet<>();
        for (int i = 0; i < studentsId.length; i++) {
            userCourseGradesSet.add(UserCourseGrade.builder()
                    .courseId(courseId)
                    .userId(studentsId[i])
                    .grade(grades[i])
                    .build());
        }
        userCourseGradeRepository.saveAll(userCourseGradesSet);
    }

    @Override
    public List<UserCourseGradeResponse> findAllByCourseId(String courseId) {
        return userCourseGradeRepository.findAllByCourseId(courseId).stream()
                .map(userCourseGrade ->
                        new UserCourseGradeResponse(userService.findUserById(userCourseGrade.getUserId()),
                                userCourseGrade.getGrade()))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserCourseGrade> findAllByUserId(String userId) {
        return userCourseGradeRepository.findAllByUserId(userId);
    }

    @Override
    public void deleteCourseGrades(String courseId) {
        List<UserCourseGrade> userCourseGrades = userCourseGradeRepository.findAllByCourseId(courseId);
        userCourseGradeRepository.deleteAll(userCourseGrades);
    }

    private UserCourseGrade getByUserIdAndCourseId(String userId, String courseId) {
        return userCourseGradeRepository.findByUserIdAndCourseId(userId, courseId).orElseThrow(() ->
                new InternalViolationException(InternalViolationType.USER_COURSE_GRADE_NOT_FOUND_EXCEPTION));
    }
}
