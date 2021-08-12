package org.example.finalProjectSpring.service;

import org.example.finalProjectSpring.dao.CourseDao;
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

    @Override
    public void save(Course course) {
        course.setNumberOfStudents(0L);
        courseDao.save(course);
    }
}
