package com.myj.study.dubboconsumer;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class NacosDubboConsumerApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(NacosDubboConsumerApplication.class)
                .run(args);
    }

}
