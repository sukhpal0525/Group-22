package org.aston.ecommerce.report.web;

import com.itextpdf.text.DocumentException;
import org.aston.ecommerce.report.ReportGenerator;
import org.aston.ecommerce.user.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


@Controller
@RequestMapping("/report")
public class ReportController {


    @GetMapping("/add")
    public String generatePdf(HttpServletResponse response) throws DocumentException, IOException {

        //BEGIN: User is only able to generate report if they are an admin. Otherwise, they will be redirected back to the homepage.
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(!(principal instanceof CustomUserDetails)) return "redirect:/home";

        CustomUserDetails currUser = (CustomUserDetails) principal;

        if(!currUser.getIsAdmin()) return "redirect:/home";

        //END

        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy_hh_mm_ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=PC_Labs_Admin_Report_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        ReportGenerator reportGenerator = new ReportGenerator(currUser);
        reportGenerator.export(response);

        return null;
    }


}
