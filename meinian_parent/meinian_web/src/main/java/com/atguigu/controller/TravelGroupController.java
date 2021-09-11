package com.atguigu.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.entity.Result;
import com.atguigu.pojo.TravelGroup;
import com.atguigu.pojo.TravelItem;
import com.atguigu.service.TravelGroupService;
import com.atguigu.service.TravelItemService;
import com.atguigu.utils.MessageConstant;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/travelgroup")
public class TravelGroupController {

    @Reference
    private TravelGroupService travelGroupService;

    @RequestMapping("/findGroupAll")
    public PageResult findGroupAll(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = travelGroupService.findGroupAll(queryPageBean);
        return pageResult;
    }

    @RequestMapping("/findAll")
    public PageResult findAll(){
        List<TravelGroup> list = travelGroupService.findAll();
        return new PageResult((long)list.size(),list);
    }



    @RequestMapping("/findById")
    public Result findGroupById(Integer id){
        try {
            TravelGroup travelGroup = travelGroupService.findGroupById(id);
            return new Result(true,MessageConstant.QUERY_TRAVELGROUP_SUCCESS,travelGroup);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_TRAVELGROUP_FAIL);
        }
    }

    @RequestMapping("/findTravelItemIdByTravelgroupId")
    public Result findTravelItemIdByTravelgroupId(Integer id){
        try {
            List<Integer> travelItemIdList = travelGroupService.findTravelItemIdByTravelgroupId(id);
            return new Result(true,MessageConstant.QUERY_TRAVELGROUP_SUCCESS,travelItemIdList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_TRAVELGROUP_FAIL);
        }
    }

    @RequestMapping("/delTravelgroup")
    public Result delGroupById(Integer id){
        try {
            travelGroupService.delGroupById(id);
            return new Result(true,MessageConstant.DELETE_TRAVELGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_TRAVELGROUP_FAIL);
        }
    }

    @RequestMapping("/add")
    public Result add(Integer[] travelItemId,@RequestBody TravelGroup travelGroup){
        try {
            travelGroupService.add(travelItemId, travelGroup);
            return new Result(true,MessageConstant.ADD_TRAVELGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_TRAVELGROUP_FAIL);
        }
    }

    @RequestMapping("/edit")
    public Result editTravelgroup(Integer[] travelItemId,@RequestBody TravelGroup travelGroup){
        try {
            travelGroupService.editTravelgroup(travelItemId, travelGroup);
            return new Result(true,MessageConstant.ADD_TRAVELGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_TRAVELGROUP_FAIL);
        }
    }

}
