package com.myj.study.sentinel.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.myj.study.sentinel.fallback.BlockHandler;
import com.myj.study.sentinel.service.EchoService;
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
     * SentinelResource属性说明：
     * value: 资源名称，必需项（不能为空）
     * blockHandler / blockHandlerClass: blockHandler 对应处理 BlockException 的函数名称，可选项。若未配置，则将 BlockException 直接抛出。blockHandler 函数访问范围需要是 public，返回类型需要与原方法相匹配，参数类型需要和原方法相匹配并且最后加一个额外的参数，类型为 BlockException。blockHandler 函数默认需要和原方法在同一个类中。若希望使用其他类的函数，则可以指定 blockHandlerClass 为对应的类的 Class 对象，注意对应的函数必需为 static 函数，否则无法解析。
     * fallback: fallback 函数名称，可选项，仅针对降级功能生效（DegradeException）。fallback 函数的访问范围需要是 public，参数类型和返回类型都需要与原方法相匹配，并且需要和原方法在同一个类中。业务异常不会进入 fallback 逻辑。
     * 若 blockHandler 和 fallback 都进行了配置，则遇到降级的时候首先选择 fallback 函数进行处理。
     * 注意 blockHandler 是处理被 block 的情况（所有类型的 BlockException），而 fallback 仅处理被降级的情况（DegradeException）。其它异常会原样抛出，Sentinel 不会进行处理。
     * @param a
     * @param b
     * @return
     */
    @RequestMapping(value = "/divide-feign", method = RequestMethod.GET)
    @SentinelResource(value = "divide-resource",
            blockHandlerClass={BlockHandler.class} ,
            blockHandler = "divideExceptionHandler",
            fallback="divideBlockHandler")
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
