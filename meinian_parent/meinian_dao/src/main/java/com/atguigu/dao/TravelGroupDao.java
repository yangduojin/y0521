package com.atguigu.dao;

import com.atguigu.pojo.TravelGroup;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface TravelGroupDao {
    Page<TravelGroup> findGroupAll(String queryString);

    void add(TravelGroup travelGroup);

    void addTravelgroupIdAndTravelitemId(Map map);

    void delTravelgroupId(Integer id);

    void delTravelgroupIdAndTravelitemId(Integer id);

    TravelGroup findGroupById(Integer id);

    List<Integer> findTravelItemIdByTravelgroupId(Integer id);

    void editTravelgroup(TravelGroup travelGroup);

    List<TravelGroup> findAll();


    List<TravelGroup> setmealFindTravelgroup(Integer id);
}
