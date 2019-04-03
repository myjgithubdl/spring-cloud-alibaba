package com.myj.study.dubboprovider;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class NacosDubboProviderApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(NacosDubboProviderApplication.class).
                web(WebApplicationType.NONE).run(args);
    }

}
