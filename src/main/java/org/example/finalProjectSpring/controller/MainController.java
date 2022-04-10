package org.example.finalProjectSpring.controller;

import org.example.finalProjectSpring.database.entity.Course;
import org.example.finalProjectSpring.database.repository.UserRepository;
import org.example.finalProjectSpring.model.dto.UserDTO;
import org.example.finalProjectSpring.model.enams.Role;
import org.example.finalProjectSpring.model.enams.Status;
import org.example.finalProjectSpring.database.entity.User;
import org.example.finalProjectSpring.services.interfaces.CourseService;
import org.example.finalProjectSpring.services.interfaces.SecurityService;
import org.example.finalProjectSpring.services.interfaces.UserCourseGradeService;
import org.example.finalProjectSpring.services.interfaces.UserService;
import org.example.finalProjectSpring.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller for all user's pages.
 *
 * @author Kateryna Kravchenko
 * @version 1.0
 */

@Controller
public class MainController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserCourseGradeService userCourseGradeService;

    @Autowired
    private UserValidator userValidator;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new UserDTO());
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") UserDTO userForm, BindingResult bindingResult) {
        userValidator.validate(userForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        userService.createStudent(userForm);
        securityService.autoLogin(userForm.getUsername(), userForm.getConfirmPassword());
        return "redirect:/welcome";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        ResourceBundle messages = ResourceBundle.getBundle("messages", LocaleContextHolder.getLocale());
        if (error != null) {
            model.addAttribute("error", messages.getString("incorrect"));
        }
        if (logout != null) {
            model.addAttribute("error", messages.getString("loggedOut"));
        }
        return "login";
    }

    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcome(Model model) {
        return findPaginated(1, "name", "asc", "", "any", model);
    }

    @RequestMapping(value = "/user/{username}", method = RequestMethod.GET)
    public String userPage(@PathVariable("username") String username, Model model) {
        User user = userService.findUserByUsername(username);
        if (user == null) {
            return "redirect:/error";
        }
        model.addAttribute("user", user);
        return "user";
    }

    @RequestMapping(value = "/page/{pageNo}", method = RequestMethod.GET)
    public String findPaginated(@PathVariable("pageNo") int pageNo, @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir, @RequestParam("teacherId") String teacherId,
                                @RequestParam("themeName") String themeName, Model model) {
        int pageSize = 10;
        Page<Course> page = null;
        if (!teacherId.isEmpty() && themeName.replaceAll(" ", "").equals("any")) {
            User teacher = userService.findUserById(teacherId);
            page = courseService.findPaginatedByTeacher(pageNo, pageSize, sortField, sortDir, teacher);
        }

        if (!themeName.replaceAll(" ", "").equals("any") && teacherId.isEmpty()) {
            page = courseService.findPaginatedByTheme(pageNo, pageSize, sortField, sortDir, themeName);
        }

        if (!teacherId.isEmpty() && !themeName.replaceAll(" ", "").equals("any")) {
            User teacher = userService.findUserById(teacherId);
            page = courseService.findPaginatedByTeacherAndTheme(pageNo, pageSize, sortField, sortDir,
                    teacher, themeName);
        }
        if (teacherId.isEmpty() && themeName.replaceAll(" ", "").equals("any")) {
            page = courseService.findPaginated(pageNo, pageSize, sortField, sortDir);
        }

        List<Course> courseList = page.getContent();

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        Optional<User> userOptional = userRepository.findByUsername(username);
        userOptional.ifPresent(user -> {
            model.addAttribute("user", user);
            model.addAttribute("userCourses", userCourseGradeService.findAllByUserId(user.getId()));
        });
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("teacherId", teacherId);
        model.addAttribute("themeName", themeName);

        model.addAttribute("coursesList", courseList);
        return "welcome";
    }
}
