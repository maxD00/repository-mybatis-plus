package com.baomidou.mybatisplus.samples.crud;

import com.baomidou.mybatisplus.samples.crud.domain.Student;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author maxD
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class StudentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void studentPageTest() throws Exception {
        MvcResult result = mockMvc.perform(get("/students")
                .param("page", "2")
                .param("per_page", "5")
                .param("count", "true")
                // 干扰参数
                .param("size", "1000")
                .param("user_name", "zhangsan"))
                .andExpect(status().isOk())
                .andExpect(header().longValue("X-Total-Count", 13))
                .andReturn();
        ObjectMapper mapper = new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        List<Student> students = mapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<List<Student>>() {
                });
        assertThat(students.size()).as("一页{}条数据", 5).isEqualTo(5);
    }

}
