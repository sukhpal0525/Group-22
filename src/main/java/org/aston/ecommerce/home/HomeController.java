package org.aston.ecommerce.home;

import org.aston.ecommerce.user.CustomUserDetails;
import org.aston.ecommerce.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;


@Controller
public class HomeController {

    @Autowired
    private UserRepository userRepo;

    @GetMapping("/")
    public String index() { return "home"; }

    @GetMapping("/home")
    public String viewHome(HttpSession session) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //User is logged in now, so delete the session
        if (principal instanceof CustomUserDetails) {
            session.invalidate();
        }

        return "home";
    }
}
