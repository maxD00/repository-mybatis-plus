package com.baomidou.mybatisplus.samples.crud.domain;

import com.baomidou.mybatisplus.samples.crud.repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author maxD
 */
@Component
public class StudentFactory {
    @Autowired
    private StudentRepo studentRepo;

    public Student create() {
        return new Student(studentRepo);
    }
}
