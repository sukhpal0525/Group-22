package org.aston.ecommerce.user.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminReport {

    @GetMapping("/admin/report")
    public String displayReport() {
        return "admin_report";
    }

}
