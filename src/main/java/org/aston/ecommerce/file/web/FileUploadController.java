package org.aston.ecommerce.file.web;

import org.aston.ecommerce.file.FileStorageImplement;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class FileUploadController {
    @Autowired
    private FileStorageImplement fileStorageService;

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
