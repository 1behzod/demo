package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CashingApplication {

    public static void main(String[] args) {
        SpringApplication.run(CashingApplication.class, args);

        //Used all topics except logging
        //Category getList() method
        //NativeQuery in ProductCustomImpl
        //Application YML
        //Formatted exceptions
    }

}
