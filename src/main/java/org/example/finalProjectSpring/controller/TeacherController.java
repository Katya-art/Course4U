package org.example.finalProjectSpring.controller;

import org.example.finalProjectSpring.database.entity.Course;
import org.example.finalProjectSpring.database.entity.User;
import org.example.finalProjectSpring.database.entity.UserCourseGrade;
import org.example.finalProjectSpring.model.enams.Condition;
import org.example.finalProjectSpring.model.enams.Grade;
import org.example.finalProjectSpring.model.responses.UserCourseGradeResponse;
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

import java.util.List;

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
    UserCourseGradeService userCourseGradeService;

    @RequestMapping(value = "/assigned_courses", method = RequestMethod.GET)
    public String teacherCourses(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        User user = userService.findUserByUsername(username);
        model.addAttribute("courses", user.getAssignedCourses());
        return "teacher_courses";
    }

    @RequestMapping(value ="/change_course_condition/{id}", method = RequestMethod.GET)
    public String startCourse(@PathVariable("id") String id, @RequestParam(value = "condition") Condition condition) {
        courseService.changeCourseCondition(id, condition);
        return "redirect:/assigned_courses";
    }

    @RequestMapping(value ="/grade_journal/{id}", method = RequestMethod.GET)
    public String gradeJournal(@PathVariable("id") String id, Model model) {
        List<UserCourseGradeResponse> userCourseGrades = userCourseGradeService.findAllByCourseId(id);
        model.addAttribute("courseId", id);
        model.addAttribute("userCourseGrades", userCourseGrades);
        model.addAttribute("grades", Grade.values());
        return "grade_journal";
    }

    @RequestMapping(value ="/save_journal/{id}", method = RequestMethod.POST)
    public String saveJournal(@PathVariable("id") String id,
                              @RequestParam(value = "students", required = false) String[] studentsId,
                              @RequestParam(value = "grades", required = false) Grade[] grades) {
        userCourseGradeService.updateUserCourseGrades(id, studentsId, grades);
        return "redirect:/assigned_courses";
    }

}
