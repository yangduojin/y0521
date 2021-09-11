package com.atguigu.dao;

import com.atguigu.pojo.Address;
import com.github.pagehelper.Page;

import java.util.List;

public interface AddressDao {
    Page<Address> findPage(String queryString);

    List<Address> findAll();

    void addAddress(Address address);

    void deleteById(Integer id);
}
