package com.atguigu.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.OrdersettingDao;
import com.atguigu.dao.SetmealDao;
import com.atguigu.dao.TravelGroupDao;
import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.pojo.Order;
import com.atguigu.pojo.OrderSetting;
import com.atguigu.pojo.Setmeal;
import com.atguigu.pojo.TravelGroup;
import com.atguigu.utils.DateUtils;
import com.atguigu.utils.RedisConstant;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.*;

@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealDao setmealDao;

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private OrdersettingDao ordersettingDao;

    public void setSetmealIdAndTravelgroupId(Integer[] travelgroupIds,Integer setmealId) {
        Map hashMap = new HashMap();
        for (Integer travelgroupId: travelgroupIds) {
            hashMap.put("setmealId",setmealId);
            hashMap.put("travelgroupId",travelgroupId);
            setmealDao.addSetmealIdAndTravelgroupId(hashMap);
        }
    }

    @Override
    public void add(Integer[] travelgroupIds, Setmeal setmeal) {
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,setmeal.getImg());
        setmealDao.add(setmeal);
        setSetmealIdAndTravelgroupId(travelgroupIds,setmeal.getId());
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page<Setmeal> page = setmealDao.findAllOrQueryThing(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public List<Setmeal> getSetmeal() {
        Page<Setmeal> allOrQueryThing = setmealDao.findAllOrQueryThing(null);

        return allOrQueryThing;
    }

    @Override
    public Setmeal findById(Integer id) {
        return setmealDao.findById(id);
    }




}