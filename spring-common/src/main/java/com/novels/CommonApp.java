package com.novels;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.novels.common.bean.Result;
import com.novels.common.properties.TokenHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
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
