package com.myj.study.nacosconfig.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
@RequestMapping("/user")
public class UserController {

    /**
     * 从Nacos读取Data Id为spring-cloud-nacos-config.properties的user.name属性
     */
    @Value("${user.name}")
    String userName;

    /**
     * 从Nacos读取Data Id为spring-cloud-nacos-config.properties的user.age
     */
    @Value("${user.age}")
    int age;

    @RequestMapping("/getUser")
    public String getUser() {
        return "Hello Nacos Config!" + "Hello " + userName + " " + age + "!";
    }

}
