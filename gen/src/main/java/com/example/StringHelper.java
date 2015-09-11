package com.example;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wudongchuan on 2015/3/25.
 */
public class StringHelper {
    /**
     * 匹配出符合的字符串
     */
    public static String matchPart(String src, String regex) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(src);
        if (m.find())
            return m.group();
        return "";
    }

    public static void main(String[] args) {
        System.out.print(matchPart("return a.b.c b_,d;", "a.b.[\\w]+"));
    }

    /**
     * 首字母大写
     *
     * @param string
     * @return
     */
    public static String toFristUpperCase(String string) {
        return string.replaceFirst(string.substring(0, 1), string.substring(0, 1).toUpperCase());
    }
}
