package com.codefans.util;

import java.math.BigDecimal;

/**
 * @Author: codefans
 * @Date: 2019-04-13 22:54
 * 数字处理工具
 */

public class NumberUtils {

    private static final BigDecimal ONE_HUNDRED = new BigDecimal(100);

    /**
     * 分转元
     * @param fen
     * @return
     */
    public static BigDecimal fenToYuan(Long fen) {
        return fen == null ? new BigDecimal("0") : new BigDecimal(fen).divide(ONE_HUNDRED);
    }

    /**
     * 元转分
     * @param yuan
     * @return
     */
    public static Long yuanToFen(BigDecimal yuan) {
        return yuan == null ? new Long("0") : yuan.multiply(ONE_HUNDRED).longValue();
    }

}
