package org.example.finalProjectSpring.controller;

import org.example.finalProjectSpring.database.entity.Course;
import org.example.finalProjectSpring.database.entity.User;
import org.example.finalProjectSpring.model.dto.CourseDTO;
import org.example.finalProjectSpring.model.dto.UserDTO;
import org.example.finalProjectSpring.model.enams.Role;
import org.example.finalProjectSpring.model.enams.Status;
import org.example.finalProjectSpring.services.interfaces.CourseService;
import org.example.finalProjectSpring.services.interfaces.UserService;
import org.example.finalProjectSpring.validator.CourseValidator;
import org.example.finalProjectSpring.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for admin's pages.
 *
 * @author Kateryna Kravchenko
 * @version 1.0
 */

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private CourseValidator courseValidator;

    @RequestMapping(value = "/add_teacher", method = RequestMethod.GET)
    public String addTeacher(Model model) {
        model.addAttribute("teacherForm", new UserDTO());
        return "add_teacher";
    }

    @RequestMapping(value = "/add_teacher", method = RequestMethod.POST)
    public String addTeacher(@ModelAttribute("teacherForm") UserDTO teacherForm, BindingResult bindingResult) {
        userValidator.validate(teacherForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "add_teacher";
        }
        userService.createTeacher(teacherForm);
        return "redirect:/welcome";
    }

    @RequestMapping(value = "/add_course", method = RequestMethod.GET)
    public String addCourse(Model model) {
        model.addAttribute("courseForm", new CourseDTO());
        model.addAttribute("teachers", userService.findAllByRole(Role.ROLE_TEACHER));
        return "add_course";
    }

    @RequestMapping(value = "/add_course", method = RequestMethod.POST)
    public String addCourse(@ModelAttribute("courseForm") CourseDTO courseForm, BindingResult bindingResult, Model model) {
        courseValidator.validate(courseForm, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("courseForm", courseForm);
            model.addAttribute("teachers", userService.findAllByRole(Role.ROLE_TEACHER));
            return "add_course";
        }
        courseService.createCourse(courseForm);
        return "redirect:/welcome";
    }

    @RequestMapping(value ="/delete_course/{id}", method = RequestMethod.GET)
    public String deleteCourse(@PathVariable("id") String id, @RequestParam("page") int pageNo,
                               @RequestParam("sortField") String sortField, @RequestParam("sortDir") String sortDir,
                               @RequestParam("teacherId") String teacherId, @RequestParam("themeName") String themeName) {
        courseService.deleteCourseById(id);
        return String.format("redirect:/page/%s?teacherId=%s&themeName=%s&sortField=%s&sortDir=%s", pageNo, teacherId, themeName,
                sortField, sortDir);
    }

    @RequestMapping(value = "/edit_course/{id}", method = RequestMethod.GET)
    public String editCourse(@PathVariable("id") String id, Model model) {
        model.addAttribute("editForm", courseService.getCourseDTO(id));
        model.addAttribute("teachers", userService.findAllByRole(Role.ROLE_TEACHER));
        return "edit_course";
    }

    @RequestMapping(value = "/edit_course/{id}", method = RequestMethod.POST)
    public String editCourse(@PathVariable("id") String id, @ModelAttribute("courseForm") CourseDTO courseForm,
                             @RequestParam("page") int pageNo, @RequestParam("sortField") String sortField,
                             @RequestParam("sortDir") String sortDir, @RequestParam("teacherId") String teacherId,
                             @RequestParam("themeName") String themeName, BindingResult bindingResult, Model model) {
        courseValidator.validate(courseForm, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("teachers", userService.findAllByRole(Role.ROLE_TEACHER));
            return "add_course";
        }
        courseService.updateCourse(id, courseForm);
        return String.format("redirect:/page/%s?teacherId=%s&themeName=%s&sortField=%s&sortDir=%s", pageNo, teacherId, themeName,
                sortField, sortDir);
    }

    @RequestMapping(value = "/students_list", method = RequestMethod.GET)
    public String studentsList(Model model) {
        model.addAttribute("studentsList", userService.findAllByRole(Role.ROLE_STUDENT));
        return "students_list";
    }

    @RequestMapping(value ="/change_student_status/{id}", method = RequestMethod.GET)
    public String blockStudent(@PathVariable("id") String id, @RequestParam String status) {
        userService.changeUserStatus(id, status);
        return "redirect:/students_list";
    }

}
