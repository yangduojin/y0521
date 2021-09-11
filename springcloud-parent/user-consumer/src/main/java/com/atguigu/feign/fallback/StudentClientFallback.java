package com.atguigu.feign.fallback;

import com.atguigu.feign.StudentClient;
import com.atguigu.pojo.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentClientFallback implements StudentClient {
    @Override
    public Student findById(Integer id) {
        Student student = new Student();
        student.setName("Service fallback , sorry  ");
        return student;
    }
}
