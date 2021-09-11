package com.atguigu.service;

import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.entity.Result;
import com.atguigu.pojo.TravelItem;

import java.util.List;

public interface TravelItemService {
    void add(TravelItem travelItem);

    PageResult findPage(QueryPageBean queryPageBean);

    void deleteById(Integer id);

    TravelItem findById(Integer id);

    void update(TravelItem travelItem);

    List<TravelItem> findItemAll();
}
