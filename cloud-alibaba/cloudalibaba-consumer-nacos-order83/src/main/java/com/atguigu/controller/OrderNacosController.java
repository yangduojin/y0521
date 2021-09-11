package com.atguigu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class OrderNacosController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${service-url.nacos-user-service}")
    private String serverUrl;

    @RequestMapping("/consumer/payment/{id}")
    public String paymentInfo(@PathVariable("id")Integer id){
        String url = serverUrl + "/payment/" + id;
        return restTemplate.getForObject(url,String.class);
    }
}
