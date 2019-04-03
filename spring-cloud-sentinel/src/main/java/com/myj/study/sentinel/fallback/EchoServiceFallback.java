package com.myj.study.sentinel.fallback;

import com.myj.study.sentinel.service.EchoService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Component
public class EchoServiceFallback implements EchoService {

    @Override
    public String echoStr(@PathVariable("str") String str) {
        return "echoStr fallback";
    }

    @Override
    public String divide(@RequestParam Integer a, @RequestParam Integer b) {
        return "divide fallback";
    }

    @Override
    public String notFound() {
        return "notFound fallback";
    }
}
