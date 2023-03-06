package org.aston.ecommerce.user.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/dashboard")
    public String viewDashboard(Model model) {

        return "admin_dashboard";
    }
}
