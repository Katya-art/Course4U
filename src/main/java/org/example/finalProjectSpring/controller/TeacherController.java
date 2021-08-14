package org.example.finalProjectSpring.controller;

import org.example.finalProjectSpring.dao.StatusDao;
import org.example.finalProjectSpring.model.Course;
import org.example.finalProjectSpring.model.User;
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

/**
 * Controller for teacher's pages.
 *
 * @author Kateryna Kravchenko
 * @version 1.0
 */

@Controller
public class TeacherController {

    @Autowired
    UserService userService;

    @Autowired
    CourseService courseService;

    @Autowired
    StatusDao statusDao;

    @RequestMapping(value = "/assigned_courses", method = RequestMethod.GET)
    public String teacherCourses(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        User user = userService.findByUsername(username);
        model.addAttribute("courses", user.getAssignedCourses());
        return "teacher_courses";
    }

    @RequestMapping(value ="/start_course/{id}", method = RequestMethod.GET)
    public String startCourse(@PathVariable("id") Long id) {
        Course course = courseService.findCourseById(id);
        course.setStatus(statusDao.getOne(2L));
        courseService.save(course);
        return "redirect:/assigned_courses";
    }

    @RequestMapping(value ="/grade_journal/{id}", method = RequestMethod.GET)
    public String gradeJournal(@PathVariable("id") Long id, Model model) {
        Course course = courseService.findCourseById(id);
        model.addAttribute("courseStudents", course.getStudents());
        return "grade_journal";
    }
}
