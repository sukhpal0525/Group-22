package org.aston.ecommerce.ErrorHandles;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

//@Controller
//public class CustomErrorController implements ErrorController {
//    @RequestMapping("/error")
//    public String handleErrors(HttpServletRequest request) {
//        //Handle Error 404, 500
//        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
//        if(status != null) {
//            Integer statusCode = Integer.valueOf(status.toString());
//            if(statusCode == HttpStatus.NOT_FOUND.value()) {
//                return "error_404";
//            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
//                return "error_500";
//            }
//        }
//        return "error";
//    }
//}
