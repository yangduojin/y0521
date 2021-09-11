package com.atguigu.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Result;
import com.atguigu.pojo.Member;
import com.atguigu.service.MemberService;
import com.atguigu.utils.MessageConstant;
import com.atguigu.utils.RedisConstant;
import com.atguigu.utils.RedisMessageConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

@RequestMapping("/login")
@RestController
public class LoginController {

    @Reference
    private MemberService memberService;

    @Autowired
    private JedisPool jedisPool;


    @RequestMapping("/check")
    public Result check(@RequestBody Map map, HttpServletResponse response){
        String telephone = (String) map.get("telephone");
        String codeInMap = (String) map.get("validateCode");
        String codeInRedis = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_LOGIN);
        if(codeInRedis == null || !codeInMap.equals(codeInRedis)){
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }

        Member member = memberService.findMemberByTelephone(map);

        if(member == null){
            member = new Member();
            member.setPhoneNumber(telephone);
            member.setRegTime(new Date());
            memberService.addMember(member);
        }

        Cookie cookie = new Cookie("login_member_telephone", telephone);
        cookie.setPath("/");
        cookie.setMaxAge(24*60*60);
        response.addCookie(cookie);
        return new Result(true,MessageConstant.LOGIN_SUCCESS);
    }
}


