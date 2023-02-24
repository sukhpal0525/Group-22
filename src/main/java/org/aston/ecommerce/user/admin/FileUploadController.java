package org.aston.ecommerce.user.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.aston.ecommerce.user.admin.FileStorage;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileUploadController {
    @Autowired
    FileStorage storage;

    @GetMapping("/")
    public String homepage() {
        return("admin/upload");
    }

    @GetMapping("admin/upload/new")
    public String newImage(Model model) {
        return("upload_form");
    }

    @PostMapping("admin/upload/create")
    public String uploadImage(Model model, @RequestParam("file")MultipartFile file) {
        return("IGNORE_ME");
    }

}
