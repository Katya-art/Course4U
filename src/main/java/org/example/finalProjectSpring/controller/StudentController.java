package org.example.finalProjectSpring.controller;

import org.example.finalProjectSpring.model.enams.Condition;
import org.example.finalProjectSpring.services.interfaces.CourseService;
import org.example.finalProjectSpring.services.interfaces.UserCourseGradeService;
import org.example.finalProjectSpring.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Locale;

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
    private UserCourseGradeService userCourseGradeService;

    @RequestMapping(value ="/enroll_course/{id}", method = RequestMethod.GET)
    public String enrollCourse(@PathVariable("id") String id, @RequestParam("page") int pageNo,
                               @RequestParam("sortField") String sortField, @RequestParam("sortDir") String sortDir,
                               @RequestParam("teacherId") String teacherId, @RequestParam("themeName") String themeName) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        userCourseGradeService.createUserCourseGrade(username, id);
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
        model.addAttribute("courseResponses", courseService.findAllByUsernameAndCondition(username, conditionString));
        model.addAttribute("user", userService.findUserByUsername(username));
        model.addAttribute("condition", Condition.valueOf(conditionString.toUpperCase()));
        return "student_courses";
    }
}
