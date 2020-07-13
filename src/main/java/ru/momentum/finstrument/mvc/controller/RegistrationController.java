package ru.momentum.finstrument.mvc.controller;

import org.springframework.web.bind.annotation.*;
import ru.momentum.finstrument.core.entity.User;
import ru.momentum.finstrument.mvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Calendar;
import java.util.Locale;

@Controller
public class RegistrationController {

    public static final String REGISTRATION = "registration";
    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new User());

        return REGISTRATION;
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("user", new User());
            return REGISTRATION;
        }
        if (!user.getPassword().equals(user.getPasswordConfirm())) {
            model.addAttribute("user", new User());
            model.addAttribute("passwordError", "Пароли не совпадают");
            return REGISTRATION;
        }
        if (!userService.saveUser(user)) {
            model.addAttribute("user", new User());
            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
            return REGISTRATION;
        }

        return "redirect:/";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        final boolean isActivated = userService.activateUser(code);

        if (!isActivated) {
            model.addAttribute("message", "User successfully activated");
        } else {
            model.addAttribute("message", "Activation code is not found!");
        }

        return "login";
    }
}
