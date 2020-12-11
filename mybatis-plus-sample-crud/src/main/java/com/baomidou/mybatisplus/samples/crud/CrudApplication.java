package com.baomidou.mybatisplus.samples.crud;

import com.baomidou.mybatisplus.samples.crud.config.scan.RepoQueryParamScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RepoQueryParamScan
public class CrudApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrudApplication.class, args);
    }
}

