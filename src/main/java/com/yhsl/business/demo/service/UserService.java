package com.yhsl.business.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yhsl.business.demo.entity.User;

import java.util.List;

/**
 * @ Author     ：zhouchao.
 * @ Date       ：Created in 2019-09-04
 * @ Description：
 */
public interface UserService extends IService<User> {

    public void saveUser(User user);
    public void deleteUser(String id);
    public void updateUser(User user);
    public User getUserById(String id);
    public User getUser(String name, String phone);
    public List<User> getUsers();
    public List<User> getUserInfo();
}
