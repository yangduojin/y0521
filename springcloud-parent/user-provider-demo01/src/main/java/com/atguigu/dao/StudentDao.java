package com.atguigu.dao;

import com.atguigu.pojo.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentDao extends JpaRepository<Student,Integer> {
}
