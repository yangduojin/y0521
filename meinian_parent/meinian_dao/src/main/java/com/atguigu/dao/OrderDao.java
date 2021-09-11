package com.atguigu.dao;

import com.atguigu.pojo.Order;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderDao {

    void addOrder(Order order);

    Order findById(Integer id);

    List<Order> findByCondition(Order order);

    Map findById4Detail(Integer id);

    List<Map<String, Object>> findSetmealNameAndCount();

    int getTodayOrderNumber(String date);

    int getTodayVisitsNumber(String date);

    int getThisWeekAndMonthOrderNumber(Map<String, Object> map);

    int getThisWeekAndMonthVisitsNumber(Map<String, Object> map);

    List<Map<String,Object>> findHotSetmeal();
}
