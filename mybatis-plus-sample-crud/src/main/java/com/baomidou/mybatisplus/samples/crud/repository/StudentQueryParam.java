package com.baomidou.mybatisplus.samples.crud.repository;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.samples.crud.config.WhereEndWithSql;
import com.baomidou.mybatisplus.samples.crud.config.WhereStartWithSql;
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
    @WhereStartWithSql("exists (select 1 from family f where f.user_id=u.id and f.name")
    @WhereEndWithSql(")")
    @TableField("safasffd")
    private String momName;
}
