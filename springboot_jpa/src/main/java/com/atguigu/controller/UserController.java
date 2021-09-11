package com.atguigu.controller;

import com.atguigu.pojo.User;
import com.atguigu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/findall")
    public List<User> findall(){
        return userService.findall();
    }

    @RequestMapping("/findById")
    public User findById(Integer id){
        return userService.findById(id);
    }

    @RequestMapping("/update")
    public void update(Integer id){
        User user = new User();
        user.setId(id);
        user.setName("yx");
        user.setUsername("haha");
        user.setPassword("123");
        userService.update(user);
    }

    @RequestMapping("/save")
    public void save(){
        User user = new User();
        user.setName("yolo");
        user.setUsername("wawa");
        user.setPassword("321");
        userService.save(user);
    }

    @RequestMapping("/del")
    public void delById(Integer id){
        userService.deleteUserById(id);
    }

}
