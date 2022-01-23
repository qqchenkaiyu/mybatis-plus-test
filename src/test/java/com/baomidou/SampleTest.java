package com.baomidou;


import com.baomidou.ant.blog.entity.User;
import com.baomidou.ant.blog.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.LinkedList;
import java.util.List;

//@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan("com.baomidou.ant.blog.mapper")
public class SampleTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        //Assert.assertEquals(5, userList.size());
        userList.forEach(System.out::println);
    }

    @Test
    public void testInsert() {

        System.out.println(("----- testInsert method test ------"));
        LinkedList<User> users = new LinkedList<>();

        for (int i = 0; i < 1 * 100; i++) {
            User user = new User().setAge(18).setName("cky").setSchool("zg").setScore(100);
            users.add(user);
        }
        userMapper.insertBatch(users);
    }

}
