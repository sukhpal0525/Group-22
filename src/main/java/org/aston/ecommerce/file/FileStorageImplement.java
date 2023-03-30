package org.aston.ecommerce.file;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageImplement implements FileStorage {
    private final Path root = Paths.get(this.getPathForImages());

    @Override
    public void init() {
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not get folder for upload!");
        }
    }

    //Saving Files
    @Override
    public void save(MultipartFile file) {
        try {
            Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new RuntimeException("That filename already exists.");
            }
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("File can't be read!");
            }
        } catch(MalformedURLException e) {
            throw new RuntimeException("Unexpected Error: " + e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root, 1).filter(path-> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Files couldn't be loaded!");
        }
    }

    //Return true if file is .jpg or .png
    @Override
    public Boolean isFileAcceptable(MultipartFile file){
        if(file == null) return true;
        if(file.isEmpty()) return true;
        String extension = "";

        int i = file.getOriginalFilename().lastIndexOf(".");
        if(i == -1) return false;
        if(i > 0){
            extension = file.getOriginalFilename().substring(i+1);
        }
        if(extension.equals("png") || extension.equals("jpg")) return true;
        return false;
    }

    public String getPathForImages() {
        File jarDir = new File(ClassLoader.getSystemClassLoader().getResource("uploads").getPath());
        String decodedPath = "";
        try{
            decodedPath = URLDecoder.decode(jarDir.getAbsolutePath(), "UTF-8");
        }catch(Exception e){

        }

        if(!jarDir.isDirectory()){
            decodedPath = "./src/main/resources/uploads";
        }

        //System.out.println(decodedPath);
        return decodedPath;
    }
}
