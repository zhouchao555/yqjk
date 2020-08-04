package com.yhsl.business.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yhsl.business.demo.entity.User;
import com.yhsl.business.demo.mapper.UserMapper;
import com.yhsl.business.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ Author     ：zhouchao.
 * @ Date       ：Created in 2019-09-04
 * @ Description：
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    public void saveUser(User user){ userMapper.insert(user); }

    public void deleteUser(String id){
        userMapper.deleteById(id);
    }

    public void updateUser(User user){
        userMapper.updateById(user);
    }

    @Override
    public User getUserById(String id) {
        return userMapper.selectById(id);
    }

    @Override
    public User getUser(String name, String phone) {
        return userMapper.findUser(name, phone);
    }

    @Override
    public List<User> getUsers() {
        return userMapper.findUsers();
    }

    @Override
    public List<User> getUserInfo() {
        return userMapper.findAll();
    }
}
