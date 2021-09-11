package com.atguigu.feign;

import com.atguigu.feign.fallback.StudentClientFallback;
import com.atguigu.pojo.Student;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "user-provider",fallback = StudentClientFallback.class)
public interface StudentClient {
    @RequestMapping("/hello/find/{id}")
    Student findById(@PathVariable("id") Integer id);
}
