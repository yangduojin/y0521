package com.atguigu.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @RequestMapping("/add")
    @PreAuthorize("hasAuthority('add')")
    public String add(){
        return "add TEST!";
    }

    @RequestMapping("/update")
    @PreAuthorize("hasAuthority('update')")
    public String update(){
        return "UPDATE TESTTTT！！";
    }

    @RequestMapping("/del")
    @PreAuthorize("hasRole('admin')")
    public String del(){
        return "ROLE_admin！！";
    }

    @RequestMapping("/find")
    @PreAuthorize("hasAuthority('find')")
    public String find(){
        return "find！！";
    }

}
