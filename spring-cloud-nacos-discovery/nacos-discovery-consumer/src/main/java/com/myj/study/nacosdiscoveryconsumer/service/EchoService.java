package com.myj.study.nacosdiscoveryconsumer.service;

import com.myj.study.nacosdiscoveryconsumer.fallback.EchoServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "spring-cloud-nacos-discovery-provider",
        fallback = EchoServiceFallback.class)
public interface EchoService {

    /**
     * 请求服务资源
     *
     * @param str
     * @return
     */
    @RequestMapping(value = "/echo/echoStr/{str}", method = RequestMethod.GET)
    String echoStr(@PathVariable("str") String str);

    /**
     * 请求服务资源
     *
     * @param a
     * @param b
     * @return
     */
    @RequestMapping(value = "/echo/divide", method = RequestMethod.GET)
    String divide(@RequestParam("a") Integer a, @RequestParam("b") Integer b);

    /**
     * 请求服务资源
     *
     * @return
     */
    @RequestMapping(value = "/notFound", method = RequestMethod.GET)
    String notFound();
}
