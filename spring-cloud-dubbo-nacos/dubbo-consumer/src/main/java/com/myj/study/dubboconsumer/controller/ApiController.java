package com.myj.study.dubboconsumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.myj.study.service.ApiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    /**
     * Reference注解标识服务消费者，version需对应于提供者的版本
     */
    @Reference(version = "1.0.0" , loadbalance = "sss")
    private ApiService apiService;

    @GetMapping("/sayParam")
    public String sayParam(@RequestParam String param) {
        return apiService.sayParam(param);
    }


    @GetMapping("/divide")
    public int divide(@RequestParam int a, @RequestParam int b) {
        return apiService.divide(a, b);
    }

}
