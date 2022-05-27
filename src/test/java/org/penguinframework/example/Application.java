package org.penguinframework.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "org.penguinframework")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
