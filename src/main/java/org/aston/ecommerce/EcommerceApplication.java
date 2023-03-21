package org.aston.ecommerce;

import java.util.Random;

import org.aston.ecommerce.file.FileStorage;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class EcommerceApplication implements CommandLineRunner {

    @Resource
    FileStorage fileStorageService;

    public static void main(String[] args) {
        SpringApplication.run(EcommerceApplication.class, args);
    }

    @Override
    public void run(String... arg) throws Exception {
        //fileStorageService.deleteAll();
        fileStorageService.init();
    }

    public static String generateString(int length) {
        Random rng = new Random();
        String characters = "abcdefghijklmnopqrstuvwxyz1234567890";
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = characters.charAt(rng.nextInt(characters.length()));
        }
        return new String(text).toUpperCase();
    }
}