package org.aston.ecommerce.file;

import lombok.extern.slf4j.Slf4j;
import org.aston.ecommerce.file.web.FileUploadController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FileService {

    @Autowired
    private FileStorage fileStorageService;

    public List<ImageInfo> getListImages(){
        List<ImageInfo> imageInfos = this.fileStorageService.loadAll().map(path -> {
            String filename = path.getFileName().toString();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(FileUploadController.class, "getImage", path.getFileName().toString()).build().toString();

            return new ImageInfo(filename, url);
        }).collect(Collectors.toList());

        return imageInfos;
    }

    public ImageInfo getImageByName(String name){
        return this.getListImages().stream()
                .filter((imageInfo) -> imageInfo.getName().equals(name))
                .findAny()
                .orElse(null);
    }

}
