package com.baomidou.mybatisplus.samples.crud;

import com.baomidou.mybatisplus.samples.crud.config.EnableRequestParamSnake;
import com.baomidou.mybatisplus.samples.crud.config.scan.RepoQueryParamScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RepoQueryParamScan
@EnableRequestParamSnake
public class CrudApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrudApplication.class, args);
    }
}

