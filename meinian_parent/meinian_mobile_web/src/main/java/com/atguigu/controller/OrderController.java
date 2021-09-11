package com.atguigu.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Result;
import com.atguigu.pojo.Order;
import com.atguigu.service.OrderService;
import com.atguigu.utils.MessageConstant;
import com.atguigu.utils.RedisMessageConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

@RequestMapping("/order")
@RestController
public class OrderController {

    @Autowired
    private JedisPool jedisPool;

    @Reference
    private OrderService orderService;

    @RequestMapping("/submit")
    public Result submitOrder(@RequestBody Map map){
        try {
            String telephone = (String) map.get("telephone");
            String codeInMap = (String) map.get("validateCode");

            String codeInRedis = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_ORDER);
            if(codeInRedis == null || !codeInRedis.equals(codeInMap)){
                return new Result(false, MessageConstant.VALIDATECODE_ERROR);
            }
            map.put("orderType", Order.ORDERTYPE_WEIXIN);
            map.put("orderStatus", Order.ORDERSTATUS_NO);
            Result result = orderService.submitOrder(map);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ORDERSETTING_FAIL);
        }
    }

    @RequestMapping("/findById")
    public Result findById4Detail(Integer id){
        Map map = null;
        try {
            map = orderService.findById4Detail(id);
            return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.QUERY_ORDER_FAIL);

        }
    }

}
