# spring-cloud-alibaba

​	随着微服务应用的发展，Spring Cloud体系中的一些组件现在已不再更新，比如Eureka和Hystrix。幸好目前阿里也将内部一些优秀的组件整合到了Spring Cloud即Spring Cloud Alibaba ，它致力于提供微服务开发的一站式解决方案。此项目包含开发分布式应用微服务的必需组件，方便开发者通过 Spring Cloud 编程模型能轻松使用这些组件来开发分布式应用服务。

​	Spring Cloud Alibaba使用了两个不同分支来分别支持 Spring Boot 1 和 Spring Boot 2。

- 0.1.x 版本适用于 Spring Boot 1
- 0.2.x 版本适用于 Spring Boot 2



​	接下来就介绍下Spring Cloud Alibaba 体系的几个组件以及在Spring Cloud中使用。



# 组件

**[Nacos](https://github.com/alibaba/Nacos)**：一个动态服务发现、配置管理和服务管理平台，并且提供了web页面管理服务和做服务配置，可以代替Eureka和Spring Cloud Config。

**[Sentinel](https://github.com/alibaba/Sentinel)**：把流量作为切入点，从流量控制、熔断降级、系统负载保护等多个维度保护服务的稳定性，可以代替Hystrix。

**[Dubbo](https://github.com/apache/incubator-dubbo)**：Apache Dubbo™ (incubating) 是一款高性能 Java RPC 框架。

**[RocketMQ](https://rocketmq.apache.org/)**：一款开源的分布式消息系统，基于高可用分布式集群技术，提供低延时的、高可靠的消息发布与订阅服务。

**[Fescar](https://github.com/alibaba/fescar)**：阿里巴巴开源产品，一个易于使用的高性能微服务分布式事务解决方案。



# Nacos

**[Nacos官网介绍](https://nacos.io/zh-cn/)**

**[Nacos安装](http://note.youdao.com/noteshare?id=f6e91094a9bb2438fc4ef9ebaa303c50&sub=200FA8E39741467BA51CF8639828E10A)**

**[Spring Cloud服务发现 - Alibaba Nacos Discovery 示例](https://github.com/myjgithubdl/spring-cloud-alibaba/tree/master/spring-cloud-nacos-discovery)**

**[Spring Cloud分布式配置 - Alibaba Nacos Config 示例](https://github.com/myjgithubdl/spring-cloud-alibaba/tree/master/spring-cloud-nacos-config)**

# Sentinel

**[Sentinel官网介绍](https://github.com/alibaba/Sentinel/wiki/%E4%BB%8B%E7%BB%8D)**

**[Sentinel 控制台介绍与使用](https://github.com/alibaba/Sentinel/wiki/%E6%8E%A7%E5%88%B6%E5%8F%B0)**

**[Spring Cloud 服务熔断 - Alibaba Sentinel示例](https://github.com/myjgithubdl/spring-cloud-alibaba/tree/master/spring-cloud-sentinel)**

# Dubbo

**[Dubbo官网介绍](http://dubbo.apache.org/zh-cn/docs/user/quick-start.html)**

**[Sentinel 控制台介绍与使用](https://github.com/alibaba/Sentinel/wiki/%E6%8E%A7%E5%88%B6%E5%8F%B0)**

**[Dubbo使用Nacos作为注册中心示例](https://github.com/myjgithubdl/spring-cloud-alibaba/tree/master/spring-cloud-dubbo-nacos)**



# RocketMQ



# Fescar

