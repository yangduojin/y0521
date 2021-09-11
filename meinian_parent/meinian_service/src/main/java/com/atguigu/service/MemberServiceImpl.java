package com.atguigu.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.MemberDao;
import com.atguigu.entity.Result;
import com.atguigu.pojo.Member;
import com.atguigu.utils.DateUtils;
import com.atguigu.utils.MessageConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;

    @Override
    public Member findMemberByTelephone(Map map) {
        String telephone = (String) map.get("telephone");
        return memberDao.findByTelephone(telephone);
    }

    @Override
    public void addMember(Member member) {
        memberDao.addMember(member);
    }

    @Override
    public List<Integer> findMemberByRegTime(List<String> lists) {

        List<Integer> countList = new ArrayList<>();
        for (String list : lists) {
            String lastDayOfMonth = DateUtils.getLastDayOfMonth(list);
            Integer count = memberDao.findMemberByRegTime(lastDayOfMonth);
            countList.add(count);
        }
        return countList;
    }
}
