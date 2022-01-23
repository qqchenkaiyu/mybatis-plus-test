package com.baomidou;


import com.baomidou.ant.blog.entity.User;
import com.baomidou.ant.blog.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Wrapper;
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
        User user = new User().setAge(18).setName("cky").setSchool("zg").setScore(100);
        userMapper.insert(user);
        userMapper.updateById(new User().setId(user.getId()).setAge(19));
        userMapper.deleteById(user.getId());
    }

    @Test
    public void testInsertBatch() {

        System.out.println(("----- testInsertBatch method test ------"));
        LinkedList<User> users = new LinkedList<>();

        for (int i = 0; i < 1 * 100; i++) {
            User user = new User().setAge(19).setName("cky").setSchool("zg").setScore(100);
            users.add(user);
        }
        userMapper.insertBatch(users);
    }
    @Test
    public void testDelete() {

        userMapper.deleteById("102");
    }
}
