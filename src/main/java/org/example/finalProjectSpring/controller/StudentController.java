package org.example.finalProjectSpring.controller;

import org.example.finalProjectSpring.model.Condition;
import org.example.finalProjectSpring.database.entity.Course;
import org.example.finalProjectSpring.database.entity.User;
import org.example.finalProjectSpring.service.CourseService;
import org.example.finalProjectSpring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

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

    @RequestMapping(value ="/enroll_course/{id}", method = RequestMethod.GET)
    public String enrollCourse(@PathVariable("id") String id, @RequestParam("page") int pageNo, @RequestParam("sortField") String sortField,
                               @RequestParam("sortDir") String sortDir, @RequestParam("teacherId") Long teacherId,
                               @RequestParam("themeName") String themeName) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        Course course = courseService.findCourseById(id);
        //Map<User, Mark> studentsMarks = course.getStudentsMarks();
        //studentsMarks.put(userService.findByUsername(username), markDao.getOne(6L));
        //course.setStudentsMarks(studentsMarks);
        //course.setNumberOfStudents(course.getStudentsMarks().size());
        courseService.save(course);
        return String.format("redirect:/page/%s?teacherId=%s&themeName=%s&sortField=%s&sortDir=%s", pageNo, teacherId,
                themeName, sortField, sortDir);
    }

    @RequestMapping(value = "/my_courses/{condition}", method = RequestMethod.GET)
    public String studentCourses(@PathVariable("condition") String conditionString, Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        User user = userService.findByUsername(username);
        //List<Course> enrolledCourses = user.getEnrolledCourses();
        List<Course> courses = new ArrayList<>();
        Condition condition = null;
        if (conditionString.equals("not_started")) {
            condition = Condition.NOT_STARTED;
        }
        if (conditionString.equals("in_progress")) {
            condition = Condition.IN_PROGRESS;
        }
        if (conditionString.equals("completed")) {
            condition = Condition.COMPLETED;
        }
//        for (Course course : enrolledCourses) {
//            if (Objects.equals(course.getCondition(), condition)) {
//                courses.add(course);
//            }
//        }
        model.addAttribute("courses", courses);
        model.addAttribute("user", user);
        model.addAttribute("condition", condition);
        return "student_courses";
    }
}
