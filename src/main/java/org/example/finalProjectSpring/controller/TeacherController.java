package org.example.finalProjectSpring.controller;

import org.example.finalProjectSpring.model.Condition;
import org.example.finalProjectSpring.database.entity.Course;
import org.example.finalProjectSpring.model.Grade;
import org.example.finalProjectSpring.database.entity.User;
import org.example.finalProjectSpring.service.CourseService;
import org.example.finalProjectSpring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String startCourse(@PathVariable("id") String id) {
        Course course = courseService.findCourseById(id);
        course.setCondition(Condition.IN_PROGRESS);
        courseService.save(course);
        return "redirect:/assigned_courses";
    }

    @RequestMapping(value ="/grade_journal/{id}", method = RequestMethod.GET)
    public String gradeJournal(@PathVariable("id") String id, Model model) {
        Course course = courseService.findCourseById(id);
        model.addAttribute("course", course);
        model.addAttribute("marks", Grade.values());
        return "grade_journal";
    }

    @RequestMapping(value ="/save_journal/{id}", method = RequestMethod.POST)
    public String saveJournal(@PathVariable("id") String id,
                              @RequestParam(value = "marks", required = false) Long[] marks,
                              @RequestParam(value = "students", required = false) Long[] students) {
        Course course = courseService.findCourseById(id);
//        Map<User, Mark> userMarkMap = new HashMap<>();
//        for (int i = 0; i < marks.length; i++) {
//            userMarkMap.put(userService.findUserById(students[i]), markService.findMarkById(marks[i]));
//        }
        //course.setStudentsMarks(userMarkMap);
        courseService.save(course);
        return "redirect:/assigned_courses";
    }

    @RequestMapping(value ="/finish_course/{id}", method = RequestMethod.GET)
    public String finishCourse(@PathVariable("id") String id) {
        Course course = courseService.findCourseById(id);
        course.setCondition(Condition.COMPLETED);
        courseService.save(course);
        return "redirect:/assigned_courses";
    }
}
