package com.atguigu.service;


import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.MemberDao;
import com.atguigu.dao.OrderDao;
import com.atguigu.dao.OrdersettingDao;
import com.atguigu.entity.Result;
import com.atguigu.pojo.Member;
import com.atguigu.pojo.Order;
import com.atguigu.pojo.OrderSetting;
import com.atguigu.utils.DateUtils;
import com.atguigu.utils.MessageConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private OrdersettingDao ordersettingDao;

    @Override
    public Result submitOrder(Map map) {
// 1、检查用户所选择的预约日期是否已经提前进行了预约设置，如果没有设置则无法进行预约
            String orderDate = (String) map.get("orderDate");
            Date date = null;
        try {
            date = DateUtils.parseString2Date(orderDate);

            OrderSetting orderSetting = ordersettingDao.findByOrderDate(date);
            if(orderSetting == null){
                return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER) ;
            }else {
// 2、检查用户所选择的预约日期是否已经约满，如果已经约满则无法预约
                int number = orderSetting.getNumber();
                int reservations = orderSetting.getReservations();
                if(reservations >= number){
                    return new Result(false,MessageConstant.ORDER_FULL);
                }
            }
// 3、检查用户是否重复预约（同一个用户在同一天预约了同一个套餐），如果是重复预约则无法完成再次预约
            String telephone = (String) map.get("telephone");
            Member member = memberDao.findByTelephone(telephone);
            if(member != null){
                Integer memberId = member.getId();
                Integer setmealId = Integer.parseInt((String) map.get("setmealId"));
                Order order = new Order(memberId,date,null,null,setmealId);

                List<Order> lists = orderDao.findByCondition(order);
                if(lists != null && lists.size() > 0){
                    return new Result(false,MessageConstant.HAS_ORDERED);
                }
            }else {
// 4、检查当前用户是否为会员，如果是会员则直接完成预约，如果不是会员则自动完成注册并进行预约
                member = new Member();
                member.setName((String)map.get("name"));
                member.setSex((String)map.get("sex"));
                member.setIdCard((String)map.get("idCard"));
                member.setPhoneNumber(telephone);
                member.setRegTime(new Date());
                memberDao.addMember(member);// 返回自增主键
            }
            orderSetting.setReservations(orderSetting.getReservations()+1);
            ordersettingDao.editReservationsByOrderDate(orderSetting);

// 5、预约成功，更新当日的已预约人数
            Order order = new Order();
            order.setMemberId(member.getId());
            order.setOrderDate(date);
            order.setOrderType((String) map.get("orderType"));
            order.setOrderStatus(Order.ORDERSTATUS_NO);
            order.setSetmealId(Integer.parseInt((String) map.get("setmealId")));
            orderDao.addOrder(order);
            System.out.println(order);
            return new Result(true,MessageConstant.ORDER_SUCCESS,order);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ORDERSETTING_FAIL);
        }
    }

    @Override
    public Map findById4Detail(Integer id) {
        Map map = orderDao.findById4Detail(id);
        Date date = (Date) map.get("orderDate");
        try {
            String parseDate2String = DateUtils.parseDate2String(date);
            map.put("orderDate",parseDate2String);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public List<Map<String, Object>> findSetmealNameAndCount() {
//        setmealNames:['套餐A','套餐B','套餐C'],
//        setmealCount:[
//        {value:1, name:'套餐A'},
//        {value:2, name:'套餐B'},
//        {value:1, name:'套餐C'},
//		]
        List<Map<String,Object>> lists = orderDao.findSetmealNameAndCount();
        return lists;
    }
}
