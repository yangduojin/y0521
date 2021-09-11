package com.atguigu.service;

import com.atguigu.entity.Result;
import com.atguigu.pojo.Member;

import java.util.List;
import java.util.Map;

public interface MemberService {
    Member findMemberByTelephone(Map map);

    void addMember(Member member);

    List<Integer> findMemberByRegTime(List<String> lists);
}
