package org.example.finalProjectSpring.controller;

import org.example.finalProjectSpring.dao.RoleDao;
import org.example.finalProjectSpring.dao.StatusDao;
import org.example.finalProjectSpring.model.Course;
import org.example.finalProjectSpring.model.User;
import org.example.finalProjectSpring.service.CourseService;
import org.example.finalProjectSpring.service.SecurityService;
import org.example.finalProjectSpring.service.UserService;
import org.example.finalProjectSpring.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for all user's pages.
 *
 * @author Kateryna Kravchenko
 * @version 1.0
 */

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private StatusDao statusDao;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

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
        userForm.setPassword(bCryptPasswordEncoder.encode(userForm.getPassword()));
        userForm.setRole(roleDao.getOne(1L));
        userForm.setStatus(statusDao.getOne(1L));
        userService.save(userForm);
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
        System.out.println("model: " + model);
        return findPaginated(1, "name", "asc", model);
    }

    @RequestMapping(value = "/user/{username}", method = RequestMethod.GET)
    public String userPage(@PathVariable("username") String username, Model model) {
        User user = userService.findByUsername(username);
        model.addAttribute("user", user);
        return "user";
    }

    @RequestMapping(value = "/page/{pageNo}", method = RequestMethod.GET)
    public String findPaginated(@PathVariable("pageNo") int pageNo, @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir, Model model) {
        int pageSize = 10;
        System.out.println("pageNo:" + pageNo + "\npageSize: " + pageSize + "\nsortField: " + sortField + "\nsortDir: " + sortDir);
        Page<Course> page = courseService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Course> courseList = page.getContent();

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        model.addAttribute("user", userService.findByUsername(username));

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("coursesList", courseList);
        return "welcome";
    }
}
