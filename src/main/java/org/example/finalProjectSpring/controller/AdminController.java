package org.example.finalProjectSpring.controller;

import org.example.finalProjectSpring.dao.RoleDao;
import org.example.finalProjectSpring.model.Course;
import org.example.finalProjectSpring.model.User;
import org.example.finalProjectSpring.service.CourseService;
import org.example.finalProjectSpring.service.UserService;
import org.example.finalProjectSpring.validator.CourseValidator;
import org.example.finalProjectSpring.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    @Autowired
    private RoleDao roleDao;

    @RequestMapping(value = "/add_teacher", method = RequestMethod.GET)
    public String addTeacher(Model model) {
        model.addAttribute("teacherForm", new User());

        return "add_teacher";
    }

    @RequestMapping(value = "/add_teacher", method = RequestMethod.POST)
    public String addTeacher(@ModelAttribute("teacherForm") User teacherForm, BindingResult bindingResult) {
        userValidator.validate(teacherForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "add_teacher";
        }

        userService.save(teacherForm, 3L);

        return "redirect:/welcome";
    }

    @RequestMapping(value = "/add_course", method = RequestMethod.GET)
    public String addCourse(Model model) {
        model.addAttribute("courseForm", new Course());
        model.addAttribute("teachers", userService.findAllByRole(roleDao.getOne(3L)));

        return "add_course";
    }

    @RequestMapping(value = "/add_course", method = RequestMethod.POST)
    public String addCourse(@ModelAttribute("courseForm") Course courseForm, BindingResult bindingResult, Model model) {
        courseValidator.validate(courseForm, bindingResult);

        if (bindingResult.hasErrors()) {
            //???
            model.addAttribute("teachers", userService.findAllByRole(roleDao.getOne(3L)));
            return "add_course";
        }
        courseForm.setTeacher(userService.findUserByFullName(courseForm.getTeacherName()));
        courseService.save(courseForm);

        return "redirect:/welcome";
    }

    @RequestMapping(value ="/delete_course/{id}", method = RequestMethod.GET)
    public String deleteCourse(@PathVariable("id") Long id) {
        courseService.deleteCourseById(id);
        return "redirect:/welcome";
    }

    @RequestMapping(value = "/edit_course/{id}", method = RequestMethod.GET)
    public String editCourse(@PathVariable("id")Long id, Model model) {
        Course course = courseService.findCourseById(id);
        course.setTeacherName(course.getTeacher().getFullName());
        model.addAttribute("editForm", course);
        model.addAttribute("teachers", userService.findAllByRole(roleDao.getOne(3L)));
        return "edit_course";
    }

    @RequestMapping(value = "/edit_course/{id}", method = RequestMethod.POST)
    public String editCourse(@ModelAttribute("courseForm") Course courseForm, BindingResult bindingResult, Model model) {
        courseValidator.validate(courseForm, bindingResult);

        if (bindingResult.hasErrors()) {
            //???
            model.addAttribute("teachers", userService.findAllByRole(roleDao.getOne(3L)));
            return "add_course";
        }
        courseForm.setTeacher(userService.findUserByFullName(courseForm.getTeacherName()));
        courseService.save(courseForm);

        return "redirect:/welcome";
    }
}
