package com.atguigu.controller;

import com.atguigu.entity.Result;
import com.atguigu.utils.MessageConstant;
import com.atguigu.utils.RedisMessageConstant;
import com.atguigu.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

@RequestMapping("validateCode")
@RestController
public class ValidateCodeController {

    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("send4Order")
    public Result send4Order(String telephone){
        try {
            String code = String.valueOf(ValidateCodeUtils.generateValidateCode(6));
            System.out.println("order code is ---> " + code);
            jedisPool.getResource().setex(telephone + RedisMessageConstant.SENDTYPE_ORDER,5*60,code);
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }

    @RequestMapping("send4Login")
    public Result send4Login(String telephone){
        try {
            String code = String.valueOf(ValidateCodeUtils.generateValidateCode(4));
            System.out.println("login code is ---> " + code);
            jedisPool.getResource().setex(telephone + RedisMessageConstant.SENDTYPE_LOGIN,5*60,code);
            return new Result(true,MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }
}
