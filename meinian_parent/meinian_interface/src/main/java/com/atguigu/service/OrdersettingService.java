package com.atguigu.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.pojo.OrderSetting;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public interface OrdersettingService {
    void add(List<OrderSetting> orderSettings);

    List<Map> getOrderSettingByMonth(String date);

    void editNumberByDate(OrderSetting orderSetting);
}
