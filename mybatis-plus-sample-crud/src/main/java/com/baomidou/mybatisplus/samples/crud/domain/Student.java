package com.baomidou.mybatisplus.samples.crud.domain;

import com.baomidou.mybatisplus.samples.crud.repository.StudentRepo;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author maxD
 */
@Data
public class Student {
    private String id;
    @NotEmpty
    private String name;
    private Integer age;
    private Integer score;
    private List<Family> familyList;
    private StudentRepo studentRepo;

    protected Student() {
    }

    public Student(StudentRepo studentRepo) {
        super();
        this.studentRepo = studentRepo;
    }
}
