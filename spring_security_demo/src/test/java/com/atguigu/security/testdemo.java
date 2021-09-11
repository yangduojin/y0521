package com.atguigu.security;

import com.atguigu.user.DateUtils;
import org.junit.Test;
import java.util.Calendar;
import java.util.Date;

public class testdemo {

    @Test
    public void testEncoder(){
        try {
            String today = DateUtils.parseDate2String(DateUtils.getToday());
            String weekMonday = DateUtils.parseDate2String(DateUtils.getThisWeekMonday());
            String weekSunday = DateUtils.parseDate2String(DateUtils.getSundayOfThisWeek());
            String firstDay4Month = DateUtils.parseDate2String(DateUtils.getFirstDay4ThisMonth());
            String lastDay4Month = DateUtils.parseDate2String(DateUtils.getLastDay4ThisMonth());
            System.out.println(today);
            System.out.println(weekMonday);
            System.out.println(weekSunday);
            System.out.println(firstDay4Month);
            System.out.println(lastDay4Month);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
