package org.example.finalProjectSpring.controller;

import org.example.finalProjectSpring.dao.CourseDao;
import org.example.finalProjectSpring.dao.MarkDao;
import org.example.finalProjectSpring.dao.StatusDao;
import org.example.finalProjectSpring.model.Course;
import org.example.finalProjectSpring.model.Mark;
import org.example.finalProjectSpring.model.User;
import org.example.finalProjectSpring.service.CourseService;
import org.example.finalProjectSpring.service.SecurityService;
import org.example.finalProjectSpring.service.UserService;
import org.example.finalProjectSpring.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Controller for student's pages.
 *
 * @author Kateryna Kravchenko
 * @version 1.0
 */

@Controller
public class StudentController {

    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private MarkDao markDao;

    @RequestMapping(value ="/enroll_course/{id}", method = RequestMethod.GET)
    public String enrollCourse(@PathVariable("id") Long id) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        Course course = courseService.findCourseById(id);
        /*Set<User> students = course.getStudents();
        students.add(userService.findByUsername(username));
        course.setStudents(students);*/
        Map<User, Mark> studentsMarks = course.getStudentsMarks();
        studentsMarks.put(userService.findByUsername(username), markDao.getOne(6L));
        course.setStudentsMarks(studentsMarks);
        courseService.save(course);
        return "redirect:/welcome";
    }

    @RequestMapping(value = "/my_courses/{status}", method = RequestMethod.GET)
    public String studentCourses(@PathVariable("status") String status, Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        User user = userService.findByUsername(username);
        Set<Course> enrolledCourses = user.getEnrolledCourses();
        Set<Course> courses = new HashSet<>();
        Long id = null;
        if (status.equals("not_started")) {
            id = 1L;
        }
        if (status.equals("in_progress")) {
            id = 2L;
        }
        if (status.equals("completed")) {
            id = 3L;
        }
        for (Course course : enrolledCourses) {
            if (Objects.equals(course.getStatus().getId(), id)) {
                courses.add(course);
            }
        }
        model.addAttribute("courses", courses);
        model.addAttribute("user", user);
        model.addAttribute("status", status);
        return "student_courses";
    }
}
