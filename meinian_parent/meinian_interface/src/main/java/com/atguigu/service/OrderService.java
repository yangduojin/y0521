package com.atguigu.service;

import com.atguigu.entity.Result;
import com.atguigu.pojo.Order;

import java.util.List;
import java.util.Map;

public interface OrderService {
    Result submitOrder(Map map);

    Map findById4Detail(Integer id);

    List<Map<String, Object>> findSetmealNameAndCount();
}
