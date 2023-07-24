package com.novels.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.core.env.Environment;

/**
 * 系统服务
 * @author 王兴
 * @date 2023/7/16 19:46
 */
@MapperScan("com.novels.system.dao")
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.novels","com.novels.system"})
public class SystemServerApp implements CommandLineRunner{

    @Autowired
    Environment environment;

    public static void main(String[] args) {
        SpringApplication.run(SystemServerApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        String[] defaultProfiles = environment.getDefaultProfiles();
        for (String s : defaultProfiles) {
            System.out.println(s);
        }
    }
}
