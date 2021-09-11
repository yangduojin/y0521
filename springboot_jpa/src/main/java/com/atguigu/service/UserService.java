package com.atguigu.service;

import com.atguigu.pojo.User;

import java.util.List;

public interface UserService {

    List<User> findall();

    void save(User user);

    void update(User user);

    void deleteUserById(Integer id);

    User findById(Integer id);

}
