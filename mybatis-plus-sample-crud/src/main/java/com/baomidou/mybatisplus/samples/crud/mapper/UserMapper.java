package com.baomidou.mybatisplus.samples.crud.mapper;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.samples.crud.domain.Student;
import com.baomidou.mybatisplus.samples.crud.entity.User;
import com.baomidou.mybatisplus.samples.crud.repository.StudentQueryParam;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * MP 支持不需要 UserMapper.xml 这个模块演示内置 CRUD 咱们就不要 XML 部分了
 * </p>
 *
 * @author hubin
 * @since 2018-08-11
 */
public interface UserMapper extends BaseMapper<User> {
    @Select("select u.id,u.name,u.age,s.avg_value as score from user u left join score s on s.user_id=u.id ${ew.customSqlSegment}")
    List<Student> findAll(@Param(Constants.WRAPPER) Wrapper<StudentQueryParam> wrapper);

    @Select("select u.id,u.name,u.age,s.avg_value as score from user u left join score s on s.user_id=u.id ${ew.customSqlSegment}")
    Page<Student> findAll(Page<Student> page, @Param(Constants.WRAPPER) Wrapper<StudentQueryParam> queryWrapper);
}
