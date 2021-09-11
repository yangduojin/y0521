package com.atguigu.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.entity.Result;
import com.atguigu.pojo.TravelItem;
import com.atguigu.service.TravelItemService;
import com.atguigu.utils.MessageConstant;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/travelItem")
public class TravelItemController {

    @Reference
    private TravelItemService travelItemService;

    @RequestMapping("/add")
    @PreAuthorize("hasAuthority('TRAVELITEM_ADD')")
    public Result addItem(@RequestBody TravelItem travelItem){
        travelItemService.add(travelItem);
        Result addResult = new Result(true, MessageConstant.ADD_TRAVELITEM_SUCCESS);
        return addResult;
    }

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = travelItemService.findPage(queryPageBean);
        return pageResult;
    }

    @RequestMapping("/delete")
    @PreAuthorize("hasAuthority('TRAVELITEM_DELETE123')")
    public Result deleteById(Integer id){
        try {
            travelItemService.deleteById(id);
            Result result = new Result(true, MessageConstant.DELETE_TRAVELITEM_SUCCESS);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            Result result = new Result(false, MessageConstant.DELETE_TRAVELITEM_FAIL + "  原因为: " + e.getMessage());
            return result;
        }
    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
        TravelItem travelItem = travelItemService.findById(id);
        return new Result(true,MessageConstant.DELETE_TRAVELITEM_SUCCESS,travelItem);
    }

    @RequestMapping("/edit")
    @PreAuthorize("hasAuthority('TRAVELITEM_EDIT')")
    public Result updateTravelItem(@RequestBody TravelItem travelItem){
        try {
            travelItemService.update(travelItem);
            return new Result(true,MessageConstant.EDIT_TRAVELITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_TRAVELITEM_FAIL);
        }
//        return new Result(true,MessageConstant.EDIT_MEMBER_SUCCESS);
    }

    @RequestMapping("/findItemAll")
    public PageResult findItemAll(){
        List<TravelItem> list = travelItemService.findItemAll();
        return new PageResult((long)list.size(),list);
    }
}
