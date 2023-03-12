package org.aston.ecommerce.contact;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class ContactController {
    @GetMapping("/contactus")
    public String ViewAbout(){
        return "contactus";
    }

}


