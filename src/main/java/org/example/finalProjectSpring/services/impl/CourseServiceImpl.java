package org.example.finalProjectSpring.services.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.finalProjectSpring.database.entity.Course;
import org.example.finalProjectSpring.database.entity.User;
import org.example.finalProjectSpring.database.entity.UserCourseGrade;
import org.example.finalProjectSpring.database.repository.CourseRepository;
import org.example.finalProjectSpring.database.repository.UserCourseGradeRepository;
import org.example.finalProjectSpring.exception.InternalViolationException;
import org.example.finalProjectSpring.exception.InternalViolationType;
import org.example.finalProjectSpring.model.dto.CourseDTO;
import org.example.finalProjectSpring.model.enams.Condition;
import org.example.finalProjectSpring.model.responses.CourseResponse;
import org.example.finalProjectSpring.services.interfaces.CourseService;
import org.example.finalProjectSpring.services.interfaces.UserCourseGradeService;
import org.example.finalProjectSpring.services.interfaces.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of {@link CourseService} interface.
 *
 * @author Kateryna Kravchenko
 * @version 1.0
 */

@Slf4j
@Service
@AllArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final UserService userService;
    private final UserCourseGradeRepository userCourseGradeRepository;

    @Override
    public void createCourse(CourseDTO courseDTO) {
        Course course = Course.builder()
                .name(courseDTO.getCourseName())
                .theme(courseDTO.getCourseTheme())
                .duration(courseDTO.getDuration())
                .teacher(userService.findUserByFullName(courseDTO.getTeacherName()))
                .build();
        courseRepository.save(course);
    }

    @Override
    public void updateCourse(String courseId, CourseDTO courseDTO) {
        Course course = findCourseById(courseId);
        Optional.of(courseDTO.getCourseName()).ifPresent(course::setName);
        Optional.of(courseDTO.getCourseTheme()).ifPresent(course::setTheme);
        Optional.of(courseDTO.getDuration()).ifPresent(course::setDuration);
        if (!courseDTO.getTeacherName().isEmpty() &&
                !courseDTO.getTeacherName().equals(course.getTeacher().getFullName())) {
            course.setTeacher(userService.findUserByFullName(courseDTO.getTeacherName()));
        }
        courseRepository.save(course);
    }

    @Override
    public void deleteCourseById(String courseId) {
        courseRepository.deleteById(courseId);
    }

    @Override
    public Course findCourseById(String courseId) {
        return courseRepository.findCourseById(courseId).orElseThrow(() ->
                new InternalViolationException(InternalViolationType.COURSE_NOT_FOUND_EXCEPTION));
    }

    @Override
    public CourseDTO getCourseDTO(String courseId) {
        Course course = findCourseById(courseId);
        return new CourseDTO(course.getName(), course.getTheme(), course.getDuration(), course.getTeacher().getFullName());
    }

    @Override
    public void changeCourseCondition(String courseId, Condition condition) {
        Course course = findCourseById(courseId);
        course.setCondition(condition);
        courseRepository.save(course);
    }

    @Override
    public Page<Course> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.courseRepository.findAll(pageable);
    }

    @Override
    public Page<Course> findPaginatedByTeacher(int pageNo, int pageSize, String sortField, String sortDirection, User teacher) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.courseRepository.findAllByTeacher(teacher, pageable);
    }

    @Override
    public Page<Course> findPaginatedByTheme(int pageNo, int pageSize, String sortField, String sortDirection, String theme) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.courseRepository.findAllByTheme(theme, pageable);
    }

    @Override
    public Page<Course> findPaginatedByTeacherAndTheme(int pageNo, int pageSize, String sortField, String sortDirection, User teacher, String theme) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.courseRepository.findAllByTeacherAndTheme(teacher, theme, pageable);
    }

    @Override
    public List<CourseResponse> findAllByUsernameAndCondition(String username, String condition) {
        User user = userService.findUserByUsername(username);
        return userCourseGradeRepository.findAllByUserId(user.getId()).stream()
                .map(userCourseGrade ->
                        new CourseResponse(findCourseById(userCourseGrade.getCourseId()), userCourseGrade.getGrade()))
                .filter(courseResponse ->
                        courseResponse.getCourse().getCondition().equals(Condition.valueOf(condition.toUpperCase())))
                .collect(Collectors.toList());
    }

}
