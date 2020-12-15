package com.baomidou.mybatisplus.samples.crud.domain;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

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
}
