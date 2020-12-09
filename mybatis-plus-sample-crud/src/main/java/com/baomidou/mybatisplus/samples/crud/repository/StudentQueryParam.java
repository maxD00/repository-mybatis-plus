package com.baomidou.mybatisplus.samples.crud.repository;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * @author maxD
 */
@Data
public class StudentQueryParam {
    @TableField("s.avg_value")
    private Integer score;
    @TableField("u.age")
    private Integer age;
}
