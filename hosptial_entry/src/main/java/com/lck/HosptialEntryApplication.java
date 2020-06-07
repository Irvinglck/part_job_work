package com.lck;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

//@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@SpringBootApplication
public class HosptialEntryApplication {

    public static void main(String[] args) {
        SpringApplication.run(HosptialEntryApplication.class, args);
    }

}
