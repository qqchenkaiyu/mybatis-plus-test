package com.baomidou.ant.blog.controller;

import com.baomidou.ant.blog.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.ant.blog.entity.User;
import java.util.List;
import org.springframework.web.bind.annotation.RestController;
/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author chenkaiyu
 * @since 2020-06-29
 */
@RestController
@RequestMapping("/blog/user")
public class UserController {
@Autowired
    UserService userServiceImpl;

    @GetMapping("/queryAll")
    public List<User> queryAll() {
        return userServiceImpl.list();
    }
}
