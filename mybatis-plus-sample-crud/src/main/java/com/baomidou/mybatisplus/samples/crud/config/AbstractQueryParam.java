package com.baomidou.mybatisplus.samples.crud.config;

import com.baomidou.mybatisplus.annotation.TableId;

/**
 * 仓库查询参数父类.
 *
 * @author maxD
 */
public abstract class AbstractQueryParam {
    @TableId("realId123Id456Id")
    private String realId123Id456Id;

    private String getRealId123Id456Id() {
        return realId123Id456Id;
    }

    private void setRealId123Id456Id(String realId123Id456Id) {
        this.realId123Id456Id = realId123Id456Id;
    }
}
