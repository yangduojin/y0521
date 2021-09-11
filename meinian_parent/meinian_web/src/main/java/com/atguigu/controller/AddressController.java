package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.entity.Result;
import com.atguigu.pojo.Address;
import com.atguigu.service.AddressService;
import com.atguigu.utils.MessageConstant;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Reference
    private AddressService addressService;

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = addressService.findPage(queryPageBean);
        return pageResult;
    }

    @RequestMapping("/findAllMaps")
    public Map findAllMaps(){
//        for(var x=0;x<data.gridnMaps.length;x++){
//            adds.push(new BMap.Point(data.gridnMaps[x].lng,data.gridnMaps[x].lat));
//            addNames.push(data.nameMaps[x].addressName);
//        }
        Map map = new HashMap<>();
        List<Address> lists = addressService.findAll();
        List<Map> gridnMaps = new ArrayList<>();
        List<Map> nameMaps = new ArrayList<>();

        for (Address list : lists) {
            Map gridnMap = new HashMap();
            Map nameMap = new HashMap();
            String lng = list.getLng();
            String lat = list.getLat();
            String addressName = list.getAddressName();
            gridnMap.put("lng",lng);
            gridnMap.put("lat",lat);
            nameMap.put("addressName",addressName);
            gridnMaps.add(gridnMap);
            nameMaps.add(nameMap);
        }
        map.put("gridnMaps",gridnMaps);
        map.put("nameMaps",nameMaps);
        return map;
    }

    @RequestMapping("/addAddress")
    public Result addAddress(@RequestBody Address address){
        addressService.addAddress(address);
        return new Result(true,"添加新地址成功");
    }

    @RequestMapping("/deleteById")
    public Result deleteById(Integer id){
        addressService.deleteById(id);
        return new Result(true, "删除地址成功");
    }

}
