package com.baomidou.mybatisplus.samples.crud.config;

import com.baomidou.mybatisplus.samples.crud.domain.Family;
import com.baomidou.mybatisplus.samples.crud.domain.FamilyFactory;
import com.baomidou.mybatisplus.samples.crud.domain.Student;
import com.baomidou.mybatisplus.samples.crud.domain.StudentFactory;
import com.baomidou.mybatisplus.samples.crud.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;


/**
 * @author maxD
 */
@Configuration
public class MyCrudMybatisConfiguration extends AbstractMybatisConfiguration {
    @Autowired
    private StudentFactory studentFactory;
    @Autowired
    private FamilyFactory familyFactory;


    @Override
    public void addObjectCreateStrategy(MybatisObjectCreateStrategyAdder adder) {
        adder.add(Student.class, (c, p) -> studentFactory.create())
                .add(User.class, (c, p) -> new User())
                .add(Family.class, (c,p) -> familyFactory.create());
    }
}
