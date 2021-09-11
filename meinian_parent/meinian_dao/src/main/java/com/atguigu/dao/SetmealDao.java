package com.atguigu.dao;


import com.atguigu.pojo.OrderSetting;
import com.atguigu.pojo.Setmeal;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface SetmealDao {

    void add(Setmeal setmeal);

    void addSetmealIdAndTravelgroupId(Map hashMap);

    Page<Setmeal> findAllOrQueryThing(String queryString);

    Setmeal findById(Integer id);
}
