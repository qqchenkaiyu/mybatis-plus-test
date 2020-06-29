package com.baomidou.ant.blog.service.impl;

import com.baomidou.ant.blog.entity.User;
import com.baomidou.ant.blog.mapper.UserMapper;
import com.baomidou.ant.blog.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chenkaiyu
 * @since 2020-06-29
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
