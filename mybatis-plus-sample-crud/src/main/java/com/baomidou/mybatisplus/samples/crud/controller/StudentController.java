package com.baomidou.mybatisplus.samples.crud.controller;

import com.baomidou.mybatisplus.samples.crud.domain.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author maxD
 */
@RestController
public class StudentController {

    @GetMapping("/students")
    public List<Student> findAll() {
        return null;
    }
}
