package com.myj.study.nacosdiscoveryprovider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/echo")
public class EchoController {

    @Autowired
    Registration registration; // 服务注册

    @Autowired
    DiscoveryClient discoveryClient;

    /**
     * 打印请求的服务的ip和端口、请求内容
     * @param string
     * @return
     */
    @RequestMapping(value = "/echoStr/{string}", method = RequestMethod.GET)
    public String echoStr(@PathVariable String string) {
        return String.format("Hello Nacos Discovery Provider , {host:%s,port:%s} , PathVariable=%s",
                registration.getHost(), registration.getPort(), string);
    }

    /**
     * 打印请求的服务的ip和端口、请求的两个数相除的结果
     * @param a
     * @param b
     * @return
     */
    @RequestMapping(value = "/divide", method = RequestMethod.GET)
    public String divide(@RequestParam Integer a, @RequestParam Integer b) {
        return String.format("Hello Nacos Discovery Provider , {host:%s,port:%s} , %d/%d=%s",
                registration.getHost(), registration.getPort(), a, b, String.valueOf(a / b));
    }

}
