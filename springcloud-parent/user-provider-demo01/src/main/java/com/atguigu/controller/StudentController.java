package com.atguigu.controller;

import com.atguigu.pojo.Student;
import com.atguigu.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/hello")
@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    @RequestMapping("/find/{id}")
    public Student findById(@PathVariable(value = "id") Integer id){
        if(id == 3){
            throw new RuntimeException("aaa");
        }
        Student student =  studentService.findById(id);
        student.setName("user-provider-demo01");
        return student ;
    }
}
