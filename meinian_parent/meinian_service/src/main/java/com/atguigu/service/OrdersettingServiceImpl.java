package com.atguigu.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.OrdersettingDao;
import com.atguigu.pojo.OrderSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = OrdersettingService.class)
@Transactional
public class OrdersettingServiceImpl implements OrdersettingService {

    @Autowired
    private OrdersettingDao ordersettingDao;

    @Override
    public void add(List<OrderSetting> orderSettings) {
        for (OrderSetting orderSetting : orderSettings) {
            long count = ordersettingDao.findOrderSettingByDate(orderSetting.getOrderDate());
            if(count > 0){
                ordersettingDao.editNumberByOrderDate(orderSetting);
            }else {
            ordersettingDao.add(orderSetting);
            }
        }
    }

    @Override
    public List<Map> getOrderSettingByMonth(String date) {
        String dateBegin = date + "-01";
        String dateEnd = date + "-31";
        Map<String,String> dateMap = new HashMap();
        dateMap.put("dateBegin",dateBegin);
        dateMap.put("dateEnd",dateEnd);
        List<OrderSetting> orderSettings = ordersettingDao.getOrderSettingByMonth(dateMap);
        List<Map> lists = new ArrayList<>();
        for (OrderSetting orderSetting : orderSettings) {
            Map hashMap = new HashMap();
            int mapDate = orderSetting.getOrderDate().getDate();
            hashMap.put("date",mapDate);
            hashMap.put("number",orderSetting.getNumber());
            hashMap.put("reservations",orderSetting.getReservations());
            lists.add(hashMap);
        }
        return lists;

        /*this.leftobj = [
                        { date: 1, number: 120, reservations: 1 },
                        { date: 3, number: 120, reservations: 1 },
                        { date: 4, number: 120, reservations: 120 },
                        { date: 6, number: 120, reservations: 1 },
                        { date: 8, number: 120, reservations: 1 }
                    ];*/

    }

    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        long count = ordersettingDao.findOrderSettingByDate(orderSetting.getOrderDate());
        if (count > 0) {
            ordersettingDao.editNumberByOrderDate(orderSetting);
        }else {
            ordersettingDao.add(orderSetting);
        }
    }
}
