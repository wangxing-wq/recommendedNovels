package com.novels;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 * @author 22343
 */
@Slf4j
@SpringBootApplication
public class CommonApp implements CommandLineRunner {


    public static void main(String[] args) {
        SpringApplication.run(CommonApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    }
}
