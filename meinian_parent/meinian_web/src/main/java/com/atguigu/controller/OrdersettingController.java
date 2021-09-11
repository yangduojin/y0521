package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Result;
import com.atguigu.pojo.OrderSetting;
import com.atguigu.service.OrdersettingService;
import com.atguigu.utils.MessageConstant;
import com.atguigu.utils.POIUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RequestMapping("/ordersetting")
@RestController
public class OrdersettingController {

    @Reference
    private OrdersettingService ordersettingService;

    @RequestMapping("/upload")
    public Result upload(MultipartFile excelFile){
        List<OrderSetting> orderSettings = new ArrayList<>();
        try {
            List<String[]> lists = POIUtils.readExcel(excelFile);
            for (String[] list : lists) {
               OrderSetting orderSetting = new OrderSetting(new Date(list[0]),Integer.parseInt(list[1]));
               orderSettings.add(orderSetting);
            }
            ordersettingService.add(orderSettings);
            return new Result(true, MessageConstant.GET_ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_ORDERSETTING_FAIL);
        }

    }

    @RequestMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String date){
        List<Map> lists = ordersettingService.getOrderSettingByMonth(date);
        return new Result(true,MessageConstant.GET_SETMEAL_LIST_SUCCESS,lists);
    }

    @RequestMapping("/editNumberByDate")
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting){
        try {
            ordersettingService.editNumberByDate(orderSetting);
            return new Result(true,MessageConstant.ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ORDERSETTING_FAIL);
        }
    }
}
