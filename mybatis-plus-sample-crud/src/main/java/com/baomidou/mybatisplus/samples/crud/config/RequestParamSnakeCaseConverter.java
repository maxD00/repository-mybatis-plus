package com.baomidou.mybatisplus.samples.crud.config;

import cn.hutool.core.util.StrUtil;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author maxD
 */
@WebFilter(urlPatterns = "/*")
public class RequestParamSnakeCaseConverter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final Map<String, String[]> formattedParams = new HashMap<>();
        // 保留原有的参数名,并对参数名进行蛇形到驼峰的转换,转换后的驼峰格式名字,若不与原有参数名冲突,则追加进formattedParams中
        for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
            formattedParams.put(entry.getKey(), entry.getValue());
            String camelKey = StrUtil.toCamelCase(entry.getKey());
            if (!formattedParams.containsKey(camelKey)) {
                formattedParams.put(camelKey, entry.getValue());
            }
        }

        filterChain.doFilter(new HttpServletRequestWrapper(request) {
            @Override
            public String getParameter(String name) {
                return formattedParams.containsKey(name) ? formattedParams.get(name)[0] : null;
            }

            @Override
            public Enumeration<String> getParameterNames() {
                return Collections.enumeration(formattedParams.keySet());
            }

            @Override
            public String[] getParameterValues(String name) {
                return formattedParams.get(name);
            }

            @Override
            public Map<String, String[]> getParameterMap() {
                return formattedParams;
            }
        }, response);
    }
}
