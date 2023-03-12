package org.aston.ecommerce.file.web;

import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.aston.ecommerce.file.FileStorage;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileUploadController {
    @Autowired
    private FileStorage fileStorageService;

//    @GetMapping("/")
//    public String homepage() {
//        return("admin/upload");
//    }
//
//    @GetMapping("admin/upload/new")
//    public String newImage(Model model) {
//        return("upload_form");
//    }
//
//    @PostMapping("admin/upload/create")
//    public String uploadImage(Model model, @RequestParam("file")MultipartFile file) {
//        return("IGNORE_ME");
//    }

    @GetMapping("/images/{filename:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        Resource file = this.fileStorageService.load(filename);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

}
