package com.myj.study.service;

/**
 * Dubbo服务提供者个消费者公用接口
 */
public interface ApiService {

    /**
     * 打印参数值
     *
     * @param param
     * @return
     */
    String sayParam(String param);


    /**
     * 两个数相除
     *
     * @param a
     * @param b
     * @return
     */
    int divide(int a, int b);

}
