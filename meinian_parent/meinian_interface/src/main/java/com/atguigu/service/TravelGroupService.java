package com.atguigu.service;

import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.entity.Result;
import com.atguigu.pojo.TravelGroup;
import com.atguigu.pojo.TravelItem;

import java.util.List;

public interface TravelGroupService {
    PageResult findGroupAll(QueryPageBean queryPageBean);

    void add(Integer[] travelItemId, TravelGroup travelGroup);

    void delGroupById(Integer id);

    TravelGroup findGroupById(Integer id);

    List<Integer> findTravelItemIdByTravelgroupId(Integer id);

    void editTravelgroup(Integer[] travelItemId, TravelGroup travelGroup);

    List<TravelGroup> findAll();
}
