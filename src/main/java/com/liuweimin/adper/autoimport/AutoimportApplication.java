package com.liuweimin.adper.autoimport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AutoimportApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutoimportApplication.class, args);
    }

}
