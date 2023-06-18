package com.novels.novel;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author 22343
 */
@MapperScan("com.novels.novel.dao")
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.novels","com.novels.novel"})
@EnableFeignClients
public class NovelServerApp {
    public static void main(String[] args) {
        SpringApplication.run(NovelServerApp.class, args);
    }
}
