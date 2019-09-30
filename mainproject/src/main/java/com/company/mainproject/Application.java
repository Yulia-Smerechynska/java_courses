package com.company.mainproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.io.IOException;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        try {
            JavaImageResizer resizer = new JavaImageResizer();
            resizer.resizeDirectoryImages();
        } catch (IOException e) {}
        // TODO: 01.10.2019 run app only when resize done

        SpringApplication.run(Application.class, args);
    }

}