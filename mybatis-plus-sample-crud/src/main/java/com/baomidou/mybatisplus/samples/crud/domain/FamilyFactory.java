package com.baomidou.mybatisplus.samples.crud.domain;

import com.baomidou.mybatisplus.samples.crud.repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author maxD
 */
@Component
public class FamilyFactory {
    @Autowired
    private StudentRepo studentRepo;

    public Family create() {
        return new Family(studentRepo);
    }
}
