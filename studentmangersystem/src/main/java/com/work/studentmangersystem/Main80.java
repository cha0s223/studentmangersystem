package com.work.studentmangersystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.work.studentmangersystem.mapper")
@EnableTransactionManagement
public class Main80 {

    public static void main(String[] args) {
        SpringApplication.run(Main80.class, args);
    }

}
