package org.aston.ecommerce.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepo;

    @GetMapping("/user-dashboard")
    public String viewHomePage(Model model) {
        model.addAttribute("header", "Welcome Group 22 User Dashboard");

        return "userDashboard";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());

        return "signup_form";
    }


}
