package com.baomidou.mybatisplus.samples.crud.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.samples.crud.config.RepoQueryWrapper;
import com.baomidou.mybatisplus.samples.crud.domain.Student;
import com.baomidou.mybatisplus.samples.crud.repository.StudentQueryParam;
import com.baomidou.mybatisplus.samples.crud.repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import java.util.List;

/**
 * @author maxD
 */
@RestController
public class StudentController {
    @Autowired
    private StudentRepo studentRepo;

    @GetMapping("/students")
    public List<Student> findAll(ServletRequest request, @ModelAttribute("webApiPage") Page<Student> page
            , Page<Student> page2, String userName, @RequestParam("user_name") String name) {
        Assert.notNull(userName, "蛇形传递的参数名,自动转换为驼峰");
        Assert.notNull(name.equals(userName), "保留原有的参数名");
        Assert.isTrue(page.getCurrent() != page2.getCurrent(), "没有使用\"webApiPage\"注解的page对象不受影响");
        Assert.isTrue(page2.getSize() == 1000, "进行默认的controller参数绑定");
        Assert.isTrue(page.getSize() != 1000, "分页框架处理生成的page对象,不进行默认的controller参数绑定");
        return studentRepo.findAll(page,
                new RepoQueryWrapper<StudentQueryParam>()
                        .gt(StudentQueryParam::getScore, 20)
                        .orderByDesc(StudentQueryParam::getScore))
                .getRecords();
    }
}
