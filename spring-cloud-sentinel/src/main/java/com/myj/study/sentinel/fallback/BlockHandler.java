package com.myj.study.sentinel.fallback;

import com.alibaba.csp.sentinel.slots.block.BlockException;

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
