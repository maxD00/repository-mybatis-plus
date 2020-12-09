package com.baomidou.mybatisplus.samples.crud.repository;

import com.baomidou.mybatisplus.samples.crud.config.RepoQueryWrapper;
import com.baomidou.mybatisplus.samples.crud.domain.Student;
import com.baomidou.mybatisplus.samples.crud.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author maxD
 */
@Repository
public class StudentRepo {
    @Autowired
    private UserMapper userMapper;

    public List<Student> findAll(RepoQueryWrapper<StudentQueryParam> queryWrapper) {
        return userMapper.findAll(queryWrapper);
    }
}
