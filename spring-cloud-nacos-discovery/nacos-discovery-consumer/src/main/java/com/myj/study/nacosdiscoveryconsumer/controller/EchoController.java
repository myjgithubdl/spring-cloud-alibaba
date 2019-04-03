package com.myj.study.nacosdiscoveryconsumer.controller;

import com.myj.study.nacosdiscoveryconsumer.service.EchoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class EchoController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private EchoService echoService;

    @Autowired
    private DiscoveryClient discoveryClient;

    /**
     * 使用RestTemplate请求资源
     *
     * @param str
     * @return
     */
    @RequestMapping(value = "/echo-rest/{str}", method = RequestMethod.GET)
    public String rest(@PathVariable String str) {
        return restTemplate.getForObject("http://spring-cloud-nacos-discovery-provider/echo/echoStr/" + str,
                String.class);
    }

    /**
     * 请求服务提供者，请求一个不存在的资源
     *
     * @return
     */
    @RequestMapping(value = "/notFound-feign", method = RequestMethod.GET)
    public String notFound() {
        return echoService.notFound();
    }

    /**
     * 请求服务提供者，调用服务提供者的/echo/divide
     *
     * @param a
     * @param b
     * @return
     */
    @RequestMapping(value = "/divide-feign", method = RequestMethod.GET)
    public String divide(@RequestParam Integer a, @RequestParam Integer b) {
        return echoService.divide(a, b);
    }

    /**
     * 请求服务提供者，调用服务提供者的/echo/echoStr/{str}
     *
     * @param str
     * @return
     */
    @RequestMapping(value = "/echo-feign/{str}", method = RequestMethod.GET)
    public String feign(@PathVariable String str) {
        return echoService.echoStr(str);
    }

    /**
     * 查找服务注册中心的指定服务信息
     *
     * @param service
     * @return
     */
    @RequestMapping(value = "/services/{service}", method = RequestMethod.GET)
    public Object client(@PathVariable String service) {
        return discoveryClient.getInstances(service);
    }

    /**
     * 查看服务注册中心的所有服务
     *
     * @return
     */
    @RequestMapping(value = "/services", method = RequestMethod.GET)
    public Object services() {
        return discoveryClient.getServices();
    }

}
