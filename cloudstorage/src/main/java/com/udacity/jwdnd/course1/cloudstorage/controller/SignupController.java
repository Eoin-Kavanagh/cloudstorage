package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignupController {

    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String getSignupPage() {
        return "signup";
    }

    @PostMapping
    public String signUpUser(@ModelAttribute User user, Model model) {
        String signUpError = null;

        if (!userService.IsUsernameAvailable(user.getUsername())) {
            signUpError = "Username is taken";
        }

        if (signUpError == null) {
            int rowsAdded = userService.createUser(user);
            if (rowsAdded < 0) {
                signUpError = "Error in User signup. Please try again";
            }
        }

        if (signUpError==null) {
            System.out.println("Sign up successful");
            model.addAttribute("signUpSuccess", true);
        } else {
            System.out.println(signUpError);
            model.addAttribute("signUpError", signUpError);
        }

        return "signup";
    }
}
