package com.atguigu.service;

import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.pojo.Address;

import java.util.List;

public interface AddressService {
    PageResult findPage(QueryPageBean queryPageBean);

    List<Address> findAll();

    void addAddress(Address address);

    void deleteById(Integer id);
}
