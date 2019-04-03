package com.myj.study.dubboprovider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.RpcContext;
import com.myj.study.service.ApiService;

@Service(version = "1.0.0")
public class ApiServiceImpl implements ApiService {

    @Override
    public String sayParam(String param) {
        int port = RpcContext.getContext().getLocalPort();
        String result = String.format("[port : %d] ApiService.sayParam( param= %s )", port, param);
        System.out.println(result);
        return result;
    }

    @Override
    public int divide(int a, int b) {
        int port = RpcContext.getContext().getLocalPort();
        int result = a / b;
        String str = String.format("[port : %s] CalculateService.divide( a= %d, b = %d ) = %d", port, a, b, result);
        System.out.println(str);
        return result;
    }
}
