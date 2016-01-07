package com.xinmei365.emojsdk.utils;

/**
 * Created by xinmei on 15/11/24.
 */
public class StringUtil {

    /**
     * 判断字符串是否为null或者""
     *
     * @param str 需要判断是否为null或者为""的字符串
     * @return 是否为null或者为空
     */
    public static boolean isNullOrEmpty(String str) {
        return (str == null || str.length() == 0);
    }
}
