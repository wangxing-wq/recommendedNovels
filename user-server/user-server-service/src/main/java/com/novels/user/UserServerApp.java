package com.novels.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Hello world!
 * @author 22343
 */
@MapperScan("com.novels.user.dao")
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.novels","com.novels.user"})
@EnableFeignClients
public class UserServerApp {

    public static void main(String[] args) {
        SpringApplication.run(UserServerApp.class, args);
    }

}
