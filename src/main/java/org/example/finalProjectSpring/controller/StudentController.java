package org.example.finalProjectSpring.controller;

import org.example.finalProjectSpring.dao.CourseDao;
import org.example.finalProjectSpring.dao.StatusDao;
import org.example.finalProjectSpring.model.Course;
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
    private SecurityService securityService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private StatusDao statusDao;

    @Autowired
    private UserValidator userValidator;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.save(userForm, 1L);

        securityService.autoLogin(userForm.getUsername(), userForm.getConfirmPassword());

        return "redirect:/welcome";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null) {
            model.addAttribute("error", "Username or password is incorrect.");
        }

        if (logout != null) {
            model.addAttribute("message", "Logged out successfully.");
        }

        return "login";
    }

    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcome(Model model) {
        model.addAttribute("coursesList", courseDao.findAll());
        return "welcome";
    }

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
        Set<User> students = course.getStudents();
        students.add(userService.findByUsername(username));
        course.setStudents(students);
        courseService.save(course);
        return "redirect:/welcome";
    }

    @RequestMapping(value = "/my_courses/{status}", method = RequestMethod.GET)
    public String myCourses(@PathVariable("status") String status, Model model) {
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
        return "my_courses";
    }
}
