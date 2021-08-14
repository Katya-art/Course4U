package org.example.finalProjectSpring.service;

import org.example.finalProjectSpring.dao.CourseDao;
import org.example.finalProjectSpring.dao.StatusDao;
import org.example.finalProjectSpring.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private StatusDao statusDao;

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
}
