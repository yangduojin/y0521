package com.atguigu.dao;

import com.atguigu.pojo.Member;

import java.util.List;
import java.util.Map;

public interface MemberDao {
    Member findByTelephone(String telephone);

    void addMember(Member member);

    Integer findMemberByRegTime(String lastDayOfMonth);

    Integer findMemberCountBeforeDate(String date);

    List<Map<String, Object>> findSetmealCount();


    int getTodayNewMember(String date);

    int getTotalMember();

    int getThisWeekAndMonthNewMember(String date);
}
