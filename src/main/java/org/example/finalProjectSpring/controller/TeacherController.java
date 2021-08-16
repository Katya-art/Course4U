package org.example.finalProjectSpring.controller;

import org.example.finalProjectSpring.dao.MarkDao;
import org.example.finalProjectSpring.dao.StatusDao;
import org.example.finalProjectSpring.model.Course;
import org.example.finalProjectSpring.model.Mark;
import org.example.finalProjectSpring.model.User;
import org.example.finalProjectSpring.service.CourseService;
import org.example.finalProjectSpring.service.MarkService;
import org.example.finalProjectSpring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    MarkService markService;

    @Autowired
    StatusDao statusDao;

    @Autowired
    MarkDao markDao;

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
        model.addAttribute("course", course);
        model.addAttribute("marks", markDao.findAll());
        return "grade_journal";
    }

    @RequestMapping(value ="/save_journal/{id}", method = RequestMethod.POST)
    public String saveJournal(@PathVariable("id") Long id,
                              @RequestParam(value = "marks", required = false) Long[] marks,
                              @RequestParam(value = "students", required = false) Long[] students) {
        Course course = courseService.findCourseById(id);
        Map<User, Mark> userMarkMap = new HashMap<>();
        for (int i = 0; i < marks.length; i++) {
            userMarkMap.put(userService.findUserById(students[i]), markService.findMarkById(marks[i]));
        }
        course.setStudentsMarks(userMarkMap);
        courseService.save(course);
        return "redirect:/assigned_courses";
    }

    @RequestMapping(value ="/finish_course/{id}", method = RequestMethod.GET)
    public String finishCourse(@PathVariable("id") Long id) {
        Course course = courseService.findCourseById(id);
        course.setStatus(statusDao.getOne(3L));
        courseService.save(course);
        return "redirect:/assigned_courses";
    }
}
