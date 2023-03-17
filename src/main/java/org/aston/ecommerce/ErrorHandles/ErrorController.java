package org.aston.ecommerce.ErrorHandles;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController {
    @RequestMapping("/error")
    public String handleErrors(HttpServletRequest request) {
        //Handle Error 403, 404, 500
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if(status != null) {
            
        }
        return "error";
    }
}
