package com.fastcode.testdocbuild11;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = { "com.fastcode.testdocbuild11", "org.springframework.versions" })
public class Testdocbuild11Application {

    public static void main(String[] args) {
        SpringApplication.run(Testdocbuild11Application.class, args);
    }
}
