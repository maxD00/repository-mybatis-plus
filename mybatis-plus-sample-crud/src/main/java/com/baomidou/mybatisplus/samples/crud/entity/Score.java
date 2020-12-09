package com.baomidou.mybatisplus.samples.crud.entity;

import lombok.Data;

/**
 * @author maxD
 */
@Data
public class Score {
    private Long id;
    private Long userId;
    private Long avgValue;
}
