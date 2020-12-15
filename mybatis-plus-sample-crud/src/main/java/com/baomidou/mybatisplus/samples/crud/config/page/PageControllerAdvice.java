package com.baomidou.mybatisplus.samples.crud.config.page;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Positive;

/**
 * @author maxD
 */
@Validated
@ControllerAdvice
public class PageControllerAdvice implements ResponseBodyAdvice<Object> {
    /**
     * rest api列表查询返回的最大数据量(条数).
     */
    public static final int MAX_WEB_LIST_SIZE = 500;
    /**
     * 默认每页条数.
     */
    public static final int DEFAULT_PER_PAGE = 10;
    /**
     * rest api列表查询,总数对应的响应头参数名.
     */
    public static final String HEADER_COUNT_PARAMETER_KEY = "X-Total-Count";

    @ModelAttribute(name = "webApiPage", binding = false)
    public Page<?> buildPage(
            @RequestParam(value = "page", required = false) @Positive(message = "page参数值为正整数") Integer page,
            @RequestParam(value = "per_page", required = false) @Positive(message = "per_page参数值为正整数") Integer perPage,
            @RequestParam(value = "count", required = false)
            @AssertTrue(message = "count参数值为true") Boolean count) {
        Page<?> pageParam;
        // 传递了查询参数page,则触发分页查询
        if (page != null) {
            pageParam = new Page<>(page, perPage == null ? DEFAULT_PER_PAGE : perPage);
        } else {
            // rest api中所有的列表查询,server端都使用分页查询处理
            pageParam = new Page<>(1, MAX_WEB_LIST_SIZE);
        }
        pageParam.setSearchCount(count != null && count);
        return pageParam;
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return Page.class.equals(returnType.getParameterType());
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        Page<?> resultBody = (Page<?>) body;
        // 如果分页时进行了总数的查询,则认为使用了count=true,在header中携带总数信息
        if (resultBody.isSearchCount()) {
            response.getHeaders().set(HEADER_COUNT_PARAMETER_KEY, String.valueOf(resultBody.getTotal()));
        }
        return resultBody.getRecords();
    }
}
