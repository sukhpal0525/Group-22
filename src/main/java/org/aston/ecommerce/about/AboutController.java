package org.aston.ecommerce.about;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class AboutController{
    @GetMapping("/aboutus")
    public String ViewAbout(){
        return "aboutus";
    }

}


