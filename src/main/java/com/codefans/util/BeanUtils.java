package com.codefans.util;

import java.util.Map;

/**
 * @Author: codefans
 * @Date: 2019-04-14 20:23
 */

public class BeanUtils {

    public static Map<?, ?> objectToMap(Object obj) {
        if(obj == null) {
            return null;
        }
        return new org.apache.commons.beanutils.BeanMap(obj);
    }

}
