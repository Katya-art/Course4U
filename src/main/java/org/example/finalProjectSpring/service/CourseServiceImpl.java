package org.example.finalProjectSpring.service;

import org.example.finalProjectSpring.dao.ConditionDao;
import org.example.finalProjectSpring.dao.CourseDao;
import org.example.finalProjectSpring.model.Course;
import org.example.finalProjectSpring.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
    public Page<Course> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.courseDao.findAll(pageable);
    }

    @Override
    public Page<Course> findPaginatedByTeacher(int pageNo, int pageSize, String sortField, String sortDirection, User teacher) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.courseDao.findAllByTeacher(teacher, pageable);
    }

    @Override
    public Page<Course> findPaginatedByTheme(int pageNo, int pageSize, String sortField, String sortDirection, String theme) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.courseDao.findAllByTheme(theme, pageable);
    }

    @Override
    public Page<Course> findPaginatedByTeacherAndTheme(int pageNo, int pageSize, String sortField, String sortDirection, User teacher, String theme) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.courseDao.findAllByTeacherAndTheme(teacher, theme, pageable);
    }

}
