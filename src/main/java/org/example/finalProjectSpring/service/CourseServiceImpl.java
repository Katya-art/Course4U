package org.example.finalProjectSpring.service;

import org.example.finalProjectSpring.dao.CourseDao;
import org.example.finalProjectSpring.dao.ConditionDao;
import org.example.finalProjectSpring.model.Course;
import org.example.finalProjectSpring.model.Mark;
import org.example.finalProjectSpring.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

/**
 * Implementation of {@link CourseService} interface.
 *
 * @author Kateryna Kravchenko
 * @version 1.0
 */

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private ConditionDao conditionDao;

    @Override
    public void save(Course course) {
        courseDao.save(course);
    }

    @Override
    public void deleteCourseById(Long id) {
        courseDao.deleteById(id);
    }

    @Override
    public Course findCourseById(Long id) {
        return courseDao.findCourseById(id);
    }

    @Override
    public Set<Course> findAllByStudentsMarks(Map<User, Mark> studentsMarks) {
        return courseDao.findAllByStudentsMarks(studentsMarks);
    }
}
