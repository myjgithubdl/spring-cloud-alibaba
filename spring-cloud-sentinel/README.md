# Spring Cloud中使用Sentinel

随着微服务的流行，服务和服务之间的稳定性变得越来越重要。Sentinel 以流量为切入点，从流量控制、熔断降级、系统负载保护等多个维度保护服务的稳定性。


在之前的[Spring Cloud使用Nocos服务注册与发现](https://github.com/myjgithubdl/spring-cloud-alibaba/tree/master/spring-cloud-nacos-discovery)一文中所创建的服务消费者应用其实已经使用到了Sentinel，只是在应用中只是用到了服务熔断的功能，接下来演示如何使用Sentinel的流量控制功能来控制资源的访问量。


## 一、启动Sentinel Dashboard
下载并启动Sentinel Dashboard，Dashboard提供的web页面对应用进行管理。

[自测安装文档](http://note.youdao.com/noteshare?id=6d4f424a662b67ec8ac2e23880998a88&sub=62BB3BCDC3D24F4C800E3ABE56604499)

[官方安装文档](https://github.com/alibaba/Sentinel/wiki/控制台)

## 二、启动服务提供者

启动[Spring Cloud使用Nocos服务注册与发现](https://github.com/myjgithubdl/spring-cloud-alibaba/tree/master/spring-cloud-nacos-discovery)一文中的服务提供者的两个实例。

![服务提供者](https://raw.githubusercontent.com/myjgithubdl/spring-cloud-alibaba/master/spring-cloud-doc/docs/assets/imgs/spring-cloud-sentinel/nacos-discovery-provider.png)



## 三、创建Sentinel应用

创建Spring Cloud集成 Sentinel，应用名称：spring-cloud-sentinel，相比于[Spring Cloud使用Nocos服务注册与发现](https://github.com/myjgithubdl/spring-cloud-alibaba/tree/master/spring-cloud-nacos-discovery)一文中的代码仅有属性配置文件中的端口不同，其余项目结构和代码几乎完全一样，此处就复制项目中的代码。

项目结构如下图：

![项目结构](https://raw.githubusercontent.com/myjgithubdl/spring-cloud-alibaba/master/spring-cloud-doc/docs/assets/imgs/spring-cloud-sentinel/overall-architecture.png)


### 3.1 修改properties配置文件

修改应用的application.properties配置文件如下：

```properties

spring.application.name=spring-cloud-sentinel
server.port=8065
management.endpoints.web.exposure.include=*
spring.cloud.nacos.discovery.server-addr=127.0.0.1:8848

#启用sentinel熔断
feign.sentinel.enabled=true

#sentinel dashboard地址
spring.cloud.sentinel.transport.dashboard=localhost:8080
spring.cloud.sentinel.eager=true


```

### 3.2 启动应用
启动应用后可以分别在Nacos Server和Sentinel Dashboard查看到应用。

Nacos Server

![启动Sentinel应用](https://raw.githubusercontent.com/myjgithubdl/spring-cloud-alibaba/master/spring-cloud-doc/docs/assets/imgs/spring-cloud-sentinel/sentinel-app-start.png)


Sentinel Dashboard


![Sentinel Dashboard中的服务](https://github.com/myjgithubdl/spring-cloud-alibaba/blob/master/spring-cloud-doc/docs/assets/imgs/spring-cloud-sentinel/sentinel-dashboard.png?raw=true)


### 3.3 在Controller的方法上增加@SentinelResource注解

在方法上增加注解 @SentinelResource("divide-resource")，在Sentinel Dashboard中就可以根据该名称来对资源进行限流，其实不使用资源名也是可以的，可以直接使用方法的url访问路径。

[@SentinelResource注解参数说明](https://github.com/alibaba/Sentinel/wiki/%E6%B3%A8%E8%A7%A3%E6%94%AF%E6%8C%81)


```java

/**
 * 请求服务提供者，调用服务提供者的/echo/divide
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


```

### 3.4 创建被限流后的异常处理类

创建@SentinelResource注解属性blockHandlerClass对应的类，也可以直接将方法定义在资源所在的类中，方法名为blockHandlerClass和fallback两个属性对应的值，方法的参数和返回值类型需与@SentinelResource注解标识的资源相同，且方法必须是pubick修饰的static方法。

创建BlockHandler类

```java

public class BlockHandler {

    /**
     * Block 异常处理函数，参数最后多一个 BlockException，其余与原函数一致.
     *
     * @param a
     * @param b
     * @param ex
     * @return
     */
    public static String divideExceptionHandler(Integer a, Integer b, BlockException ex) {
        ex.printStackTrace();
        return String.format("被Sentinel限流,异常信息：%s", ex.getMessage());
    }

    /**
     * @param a
     * @param b
     * @return
     */
    public static String divideBlockHandler(Integer a, Integer b) {
        System.out.println("divideBlockHandler");
        return String.format("被Sentinel限流,异常信息");

    }

}


```



### 3.5 访问应用

重启应用并使用地址http://127.0.0.1:8065/divide-feign?a=10&b=2访问应用后可以看到访问结果如下：

![访问结果](https://github.com/myjgithubdl/spring-cloud-alibaba/blob/master/spring-cloud-doc/docs/assets/imgs/spring-cloud-sentinel/result-1.png?raw=true)


此时刷新Sentinel Dashboard可以看到资源名

![Sentinel Dashboard结果](https://github.com/myjgithubdl/spring-cloud-alibaba/blob/master/spring-cloud-doc/docs/assets/imgs/spring-cloud-sentinel/sentinel-dashboard-2.png?raw=true)




### 3.6 设置资源流量

在Sentinel Dashboard中设置资源divide-resource的QPS值为1

![设置资源阈值](https://github.com/myjgithubdl/spring-cloud-alibaba/blob/master/spring-cloud-doc/docs/assets/imgs/spring-cloud-sentinel/sentinel-fllow.png?raw=true)

设置资源后在1秒内刷新两次http://127.0.0.1:8065/divide-feign?a=10&b=2可以看到第二次刷新已经被阻塞，说明流控已经生效。

![设置阈值后结果](https://github.com/myjgithubdl/spring-cloud-alibaba/blob/master/spring-cloud-doc/docs/assets/imgs/spring-cloud-sentinel/sentinel-fllow-result.png?raw=true)