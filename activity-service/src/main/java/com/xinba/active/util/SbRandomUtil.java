package com.xinba.active.util;

import cn.hutool.core.util.RandomUtil;

public class SbRandomUtil extends RandomUtil {

    public static String randomString(int length) {
        return randomString("abcdefghijkmnpqrstuvwxyz23456789", length);
    }

}
