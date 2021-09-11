package com.atguigu.service.impl;

import com.atguigu.dao.UserDao;
import com.atguigu.pojo.User;
import com.atguigu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<User> findall() {
        String key = "allkeys";
        List<User> users = (List<User>) redisTemplate.boundValueOps(key).get();
        if(users != null){
            return users;
        }

        users = userDao.findall();

        if(users != null && users.size() > 0 ){
            redisTemplate.boundValueOps(key).set(users);
        }

        return users;
    }


}
