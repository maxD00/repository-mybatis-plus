package com.baomidou.mybatisplus.samples.crud.config.page;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotEmpty;
import java.text.MessageFormat;

/**
 * @author maxD
 */
@ControllerAdvice
public class PageControllerAdvice {
    /**
     * rest api列表查询返回的最大数据量(条数).
     */
    public static final int MAX_WEB_LIST_SIZE = 500;
    /**
     * 默认每页条数.
     */
    public static final int DEFAULT_PER_PAGE = 10;

    @ModelAttribute(name = "webApiPage", binding = false)
    public <T> Page<T> buildPage(@RequestParam(value = "page", required = false) Integer page
            , @RequestParam(value = "per_page", required = false) Integer perPage) {
        PageDTO pageDTO = new PageDTO(page, perPage);
        // 传递了查询参数page,则触发分页查询
        if (pageDTO.getPage() != null) {
            return pageDTO.convertPage();
        } else {
            // rest api中所有的列表查询,server端都使用分页查询处理
            return new Page<>(1, MAX_WEB_LIST_SIZE);
        }
    }

    @Data
    @AllArgsConstructor
    static class PageDTO {
        private Integer page;
        private Integer perPage;

        public <T> Page<T> convertPage() {
            if (this.perPage == null) {
                return new Page<>(page, DEFAULT_PER_PAGE);
            }
            return new Page<>(page, perPage);
        }

        @Override
        public String toString() {
            return MessageFormat.format("page: {0};perPage: {1}", page, perPage);
        }
    }
}
