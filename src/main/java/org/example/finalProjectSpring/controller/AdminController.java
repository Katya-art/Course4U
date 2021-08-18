package org.example.finalProjectSpring.controller;

import org.example.finalProjectSpring.dao.RoleDao;
import org.example.finalProjectSpring.dao.ConditionDao;
import org.example.finalProjectSpring.dao.StatusDao;
import org.example.finalProjectSpring.model.Course;
import org.example.finalProjectSpring.model.User;
import org.example.finalProjectSpring.service.CourseService;
import org.example.finalProjectSpring.service.UserService;
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

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private ConditionDao conditionDao;

    @Autowired
    private StatusDao statusDao;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

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
        teacherForm.setPassword(bCryptPasswordEncoder.encode(teacherForm.getPassword()));
        teacherForm.setRole(roleDao.getOne(3L));
        userService.save(teacherForm);
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
        courseForm.setCondition(conditionDao.getOne(1L));
        courseService.save(courseForm);
        return "redirect:/welcome";
    }

    @RequestMapping(value ="/delete_course/{id}", method = RequestMethod.GET)
    public String deleteCourse(@PathVariable("id") Long id, @RequestParam("page") int pageNo,
                               @RequestParam("sortField") String sortField, @RequestParam("sortDir") String sortDir,
                               Model model) {
        courseService.deleteCourseById(id);
        return String.format("redirect:/page/%s?sortField=%s&sortDir=%s", pageNo, sortField, sortDir);
    }

    @RequestMapping(value = "/edit_course/{id}", method = RequestMethod.GET)
    public String editCourse(@PathVariable("id")Long id, @ModelAttribute("courseForm") Course courseForm, Model model) {
        Course course = courseService.findCourseById(id);
        course.setTeacherName(course.getTeacher().getFullName());
        model.addAttribute("editForm", course);
        model.addAttribute("teachers", userService.findAllByRole(roleDao.getOne(3L)));
        return "edit_course";
    }

    @RequestMapping(value = "/edit_course/{id}", method = RequestMethod.POST)
    public String editCourse(@PathVariable("id")Long id, @ModelAttribute("courseForm") Course courseForm,
                             @RequestParam("page") int pageNo, @RequestParam("sortField") String sortField,
                             @RequestParam("sortDir") String sortDir, BindingResult bindingResult, Model model) {
        courseValidator.validate(courseForm, bindingResult);
        if (bindingResult.hasErrors()) {
            //???
            model.addAttribute("teachers", userService.findAllByRole(roleDao.getOne(3L)));
            return "add_course";
        }
        Course course = courseService.findCourseById(id);
        course.setName(courseForm.getName());
        course.setTheme(courseForm.getTheme());
        course.setDuration(courseForm.getDuration());
        course.setTeacher(userService.findUserByFullName(courseForm.getTeacherName()));
        courseService.save(course);
        return String.format("redirect:/page/%s?sortField=%s&sortDir=%s", pageNo, sortField, sortDir);
    }

    @RequestMapping(value = "/students_list", method = RequestMethod.GET)
    public String studentsList(Model model) {
        model.addAttribute("studentsList", userService.findAllByRole(roleDao.getOne(1L)));
        return "students_list";
    }

    @RequestMapping(value ="/block_student/{id}", method = RequestMethod.GET)
    public String blockStudent(@PathVariable("id") Long id) {
        User student = userService.findUserById(id);
        student.setStatus(statusDao.getOne(2L));
        userService.save(student);
        return "redirect:/students_list";
    }

    @RequestMapping(value ="/unlock_student/{id}", method = RequestMethod.GET)
    public String unlockStudent(@PathVariable("id") Long id) {
        User student = userService.findUserById(id);
        student.setStatus(statusDao.getOne(1L));
        userService.save(student);
        return "redirect:/students_list";
    }
}
