package com.atguigu.controller;


import com.atguigu.pojo.Student;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/hello")
@RestController
public class StudentController {

    @RequestMapping("/stu")
    public List<Student> stu(){
        List<Student> stus = new ArrayList<>();
        stus.add(new Student("王五", "深圳", 25));
        stus.add(new Student("李四", "北京", 23));
        stus.add(new Student("赵六", "上海", 26));
        return stus;
    }
}
