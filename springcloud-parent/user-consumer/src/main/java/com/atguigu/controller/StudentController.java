package com.atguigu.controller;


import com.atguigu.pojo.Student;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/consumer")
@DefaultProperties(defaultFallback = "fallbackmeth")
public class StudentController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient client;

        public Student fallbackmeth(){
        Student student = new Student();
        student.setName("this is hystrix,hello  ");
        return student;
    }

    public Student failBack(Integer id){
        Student student = new Student();
        student.setName("hystrix,hello  ");
        return student;
    }
//    @HystrixCommand(fallbackMethod = "failBack")
    @HystrixCommand
    @GetMapping("/{id}")
    public Student queryById(@PathVariable(value = "id") Integer id){

        String url = "http://user-provider/hello/find/"+id;
//        List<ServiceInstance> instances = client.getInstances("user-provider");
//        ServiceInstance serviceInstance = instances.get(0);
//        String url = "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + "/hello/find/"+id;
        return restTemplate.getForObject(url,Student.class);
    }
}
