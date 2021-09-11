package com.atguigu;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class YxTest {

    @Autowired
    private RestTemplate restTemplate;
    
    @Test
    public void test01(){
        String url = "http://localhost:8080/hello/stu";
        String forObject = restTemplate.getForObject(url, String.class);
        System.out.println(forObject);
    }
}
