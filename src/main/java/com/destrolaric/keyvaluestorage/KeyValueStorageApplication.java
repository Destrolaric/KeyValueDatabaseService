package com.destrolaric.keyvaluestorage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class KeyValueStorageApplication {

    public static void main(String[] args) {
        SpringApplication.run(KeyValueStorageApplication.class, args);
    }

}
