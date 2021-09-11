package com.atguigu.controller;

import com.atguigu.feign.StudentClient;
import com.atguigu.pojo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feign")
public class ConsumerFeignController {

    @Autowired
    private StudentClient studentClient;

    @RequestMapping("/{id}")
    public Student findStuById(@PathVariable(value = "id")Integer id){
        return studentClient.findById(id);
    }
}
