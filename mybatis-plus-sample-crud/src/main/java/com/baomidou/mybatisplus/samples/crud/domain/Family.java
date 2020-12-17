package com.baomidou.mybatisplus.samples.crud.domain;

import com.baomidou.mybatisplus.samples.crud.repository.StudentRepo;
import lombok.Data;

/**
 * @author maxD
 */
@Data
public class Family {
    private String id;
    private String userId;
    private String relation;
    private String name;
    private StudentRepo studentRepo;

    protected Family() {
        super();
    }

    public Family(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }
}
