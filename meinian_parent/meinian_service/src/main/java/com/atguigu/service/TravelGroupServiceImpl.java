package com.atguigu.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.TravelGroupDao;
import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.pojo.TravelGroup;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = TravelGroupService.class)
@Transactional
public class TravelGroupServiceImpl implements TravelGroupService {

    @Autowired
    private TravelGroupDao travelGroupDao;

    @Override
    public PageResult findGroupAll(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page<TravelGroup> page = travelGroupDao.findGroupAll(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void add(Integer[] travelItemIds, TravelGroup travelGroup) {
        travelGroupDao.add(travelGroup);
        setTravelgroupIdAndTravelitemId(travelItemIds, travelGroup);
//        for (Integer travelitemId : travelItemIds) {
//            Map map = new HashMap<>();
//            map.put("travelItemId",travelitemId);
//            map.put("travelgroupId",travelGroup.getId());
//            travelGroupDao.addTravelgroupIdAndTravelitemId(map);
//        }
    }

    public void setTravelgroupIdAndTravelitemId(Integer[] travelItemIds,TravelGroup travelGroup){
        for (Integer travelitemId : travelItemIds) {
            Map map = new HashMap<>();
            map.put("travelItemId",travelitemId);
            map.put("travelgroupId",travelGroup.getId());
            travelGroupDao.addTravelgroupIdAndTravelitemId(map);
        }
    }

    @Override
    public void delGroupById(Integer id) {
        travelGroupDao.delTravelgroupIdAndTravelitemId(id);
        travelGroupDao.delTravelgroupId(id);
    }

    @Override
    public TravelGroup findGroupById(Integer id) {
        return travelGroupDao.findGroupById(id);
    }

    @Override
    public List<Integer> findTravelItemIdByTravelgroupId(Integer id) {
        return travelGroupDao.findTravelItemIdByTravelgroupId(id);
    }

    @Override
    public void editTravelgroup(Integer[] travelItemId, TravelGroup travelGroup) {
        travelGroupDao.editTravelgroup(travelGroup);
        travelGroupDao.delTravelgroupIdAndTravelitemId(travelGroup.getId());
        setTravelgroupIdAndTravelitemId(travelItemId, travelGroup);
    }

    @Override
    public List<TravelGroup> findAll() {
        return travelGroupDao.findAll();
    }
}