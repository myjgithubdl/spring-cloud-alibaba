# Nacos作为服务配置中心

Nacos实现了动态服务发现和管理（Spring Cloud Eureka的功能）、服务配置管理（Spring Cloud Config）的功能，并且提供了web界面操作，这使管理微服务应用变得更简单，同时官方说Nacos是经受了双十一的考验，本文演示如何使用Nocos作为Spring Cloud应用的服务注册中心。

# 一、启动Nacos Server

下载并启动Nacos Server

[自测安装文档](http://note.youdao.com/noteshare?id=f6e91094a9bb2438fc4ef9ebaa303c50&sub=200FA8E39741467BA51CF8639828E10A)

[官方安装文档](https://nacos.io/en-us/docs/quick-start.html)

Nacos Server成功启动后，下面就用Spring cloud工程演示如何使用Nacos Server作为微服务的配置中心。

# 二、创建应用

## 1. 修改maven配置文件

修改maven配置文件pom.xml文件中的parent和dependencies，本在测试的过程中需使用spring-boot版本为2.0.4.RELEASE，在其他版本中无法动态刷新配置，官方也给出了说明，如下图

![设置阈值后结果](https://raw.githubusercontent.com/myjgithubdl/spring-cloud-alibaba/master/spring-cloud-doc/docs/assets/imgs/spring-cloud-nacos-config/nacos-config-dependencies.png?raw=true)



下面是主要的pom.xml文件内容

``` xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.0.4.RELEASE</version><!-- 测试发现只有在该版本中自动刷新有效 -->
        <!--<version>2.1.2.RELEASE</version>-->
        <relativePath/>
    </parent>

    <groupId>com.myj.study</groupId>
    <artifactId>spring-cloud-nacos-config</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>spring-cloud-nacos-config</name>
    <description>Nocas配置</description>

    <properties>
        <java.version>1.8</java.version>

        <spring-boot.version>2.0.4.RELEASE</spring-boot.version>
        <spring-cloud.version>Finchley.RELEASE</spring-cloud.version>

        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
            <version>0.2.1.RELEASE</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <!--<dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>${spring-boot.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>-->

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>

```

## 2、增加配置文件

在resources目录下创建配置文件bootstrap.properties，在配置文件中设置应用名称、端口、Nacos Server的地址。

spring.application.name的值将用于构建Nacos配置管理中dataId。

在Nacos Spring Cloud，dataId的格式如下：

```
${prefix}-${spring.profile.active}.${file-extension}
```

- prefix默认情况下的值是spring.application.name的值。您还可以在spring.cloud.nacos.config.prefix中配置这个值。
- spring.profile.active是当前环境的profile文件。有关详细信息，请参阅Spring Boot文档。注:当值为spring.profile.active为空时，相应的连字符-将被删除，dataId的格式变为:${prefix}.${file-extension}。
- file-exetension是配置内容的数据格式，可以在 spring.cloud.nacos.config.file-extension中配置。目前只支持properties和yaml类型。

如有下配置文件，Nacos Server中的Data ID即为：**spring-cloud-nacos-config.properties**




``` properties
spring.application.name=spring-cloud-nacos-config

server.port=8061

#nacos server 地址
spring.cloud.nacos.config.server-addr=127.0.0.1:8848

#spring-cloud-alibaba-nacos-config.properties
#user.name=Myron
#user.age=26

spring.cloud.nacos.config.file-extension=properties
#spring.cloud.nacos.config.file-extension=yaml

#spring.cloud.nacos.config.shared-dataids=bootstrap.properties,spring-cloud-alibaba-nacos-config.properties
#spring.cloud.nacos.config.refreshable-dataids=spring-cloud-alibaba-nacos-config.properties
```

## 3、创建Controller

创建Controller读取Nacos Server中配置文件的属性，添加Spring Cloud的原生@RefreshScope注释，支持配置更新的自动刷新:


``` java
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

```

## 4、配置Nacos Data Id

在浏览器中打开Nacos Server控制台页面，在配置管理>>配置列表中新增配置Data Id为spring-cloud-nacos-config.properties

内容如下

```properties
user.name=Myron
user.age=25
```



![设置阈值后结果](https://raw.githubusercontent.com/myjgithubdl/spring-cloud-alibaba/master/spring-cloud-doc/docs/assets/imgs/spring-cloud-nacos-config/nacos-config-data-id.png)



## 5、启动Spring应用

完成上述Spring应用和Nacos Server Data Id的配置后就可以启动应用了。

其实在应用的properties中并没有设置如下两个属性的值。

```
@Value("${user.name}")
String userName;

@Value("${user.age}")
int age;
```

如果没有使用Nacos Server作为配置中心的情况下直接启动应用时会报日下错误。

```
Caused by: java.lang.IllegalArgumentException: Could not resolve placeholder 'user.age' in value "${user.age}"
```

现在使用Nacos Server作为配置中心，启动应用不但不会报错而且会去读取配置中心的配置文件内容，在启动的时候注意有这么几行日志读取配置中心日志文件的配置。

![设置阈值后结果](https://raw.githubusercontent.com/myjgithubdl/spring-cloud-alibaba/master/spring-cloud-doc/docs/assets/imgs/spring-cloud-nacos-config/nacos-config-load-data-id.png)



## 6、验证配置
访问url：http://127.0.0.1:8061/user/getUser 后发现已经读取到了配置中心配置的文件内容。





![应用启动后读取到的值](https://raw.githubusercontent.com/myjgithubdl/spring-cloud-alibaba/master/spring-cloud-doc/docs/assets/imgs/spring-cloud-nacos-config/result-1.png)



为了验证应用是否会自动刷新到Nacos Server中Data Id配置所修改后的内容，现修改Data Id为spring-cloud-nacos-config.properties中配置内容如下。

![设置阈值后结果](https://raw.githubusercontent.com/myjgithubdl/spring-cloud-alibaba/master/spring-cloud-doc/docs/assets/imgs/spring-cloud-nacos-config/nacos-config-data-id-update.png)



再次访问url：http://127.0.0.1:8061/user/getUser 后发现已经读取到了配置中心修改后的文件内容。



![设置阈值后结果](https://raw.githubusercontent.com/myjgithubdl/spring-cloud-alibaba/master/spring-cloud-doc/docs/assets/imgs/spring-cloud-nacos-config/result-2.png)



