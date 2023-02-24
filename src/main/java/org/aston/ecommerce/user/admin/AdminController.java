package org.aston.ecommerce.user.admin;

import org.aston.ecommerce.user.User;
import org.aston.ecommerce.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("admin/dashboard")
    public String adminView(Model model) {
        model.addAttribute("header","Admin Dashboard");
        return "admin/dashboard";
    }

    //This method needs to check if the user is an admin or not.
    //Needs an E-Mail, checks the E-Mail is active, and then checks if they're an admin
    //Leave for now, should it go here?
    public Boolean adminCheck(User user) {
        //Check if user is an Admin
        return user.isAdmin();
    }

    //Needs to set an Admin if a command is issued.
    @PostMapping("admin/set")
    public void setAdmin(User user) {
        user.setAdmin(true);
    }

    @PostMapping("admin/products")
    public String displayProducts() {
        String test = "Hello World";
        return test;
    }

    @PostMapping("admin/createProduct")
    public void createProduct() {

    }

    @PostMapping("admin/editProduct")
    public void editProduct() {

    }

    //Create a report for admins to view
    //Report should include:
    @GetMapping("admin/report")
    public void report() {

    }

}
