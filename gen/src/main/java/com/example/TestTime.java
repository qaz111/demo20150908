package com.example;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wudongchuan on 2015/3/11.
 */
public class TestTime {
    public static void main(String[] args) {
//        System.out.print(Format.YYYYMMDD_H.str2date("1426042885"));

//    ④时间：时间为系统发送的时间
//    时间戳时间格式制定：（请参考手机短信）
//    当天：显示24小时制，hh:mm
//    昨天（当天0点之前的24小时内）：显示“昨天”
//    前天至一周内（当天0点的24小时之前的5天内）：显示“星期x”
//    一周之前（当天0点的7天之前）显示“yyyy-mm-dd”

        long startTime = 1425567588;
        long currentTime = System.currentTimeMillis()/1000;

        int dayDiffer = (int) getRawDayDiffer(startTime, currentTime);

        if (dayDiffer == 0) {
            System.out.print(Format.HHMM.s2date(startTime));
        } else if (dayDiffer == 1) {
            System.out.print("昨天");
        } else if (dayDiffer < 7) {
            System.out.print(Format.EEEE.s2date(startTime));
        } else {
            System.out.print(Format.YYYYMMDD_H.s2date(startTime));
        }
    }

    public static long getRawDays(long time) {
        return (time + (60 * 60 * 8)) / (60 * 60 * 24);
    }

    public static long getRawDayDiffer(long startTime, long endTime) {
        int dayStart = (int) getRawDays(startTime);
        int dayCurrent = (int) (getRawDays(endTime));
        int dayDiffer = dayCurrent - dayStart;
        return dayDiffer;
    }

    /**
     * 时间戳转日期并格式化
     */
    public enum Format {
        /**
         * 日期格式化_X表示用/分开，_H表示用-分开，_C表示用中文年月日分开
         */
        YYYYMMDD_X("yyyy/MM/dd"), YYYYMMDD_H("yyyy-MM-dd"), MMDD_C("MM月dd日"), MMDDEHHMM_C("MM月dd日(E)HH:mm"), YYYYMMDDHHMM_C("yyyy年MM月dd日 HH:mm"), HHMM("HH:mm"), EEEE("EEEE");

        String strFormat;

        Format(String strFormat) {
            this.strFormat = strFormat;
        }

        /**
         * 时间戳转日期并格式化
         *
         * @param s 秒
         * @return
         */
        public String s2date(long s) {
            return new SimpleDateFormat(strFormat).format(new Date(s * 1000));
        }

        public String str2date(String s, String strFormat1) throws Exception {
            return new SimpleDateFormat(strFormat).format(new SimpleDateFormat(strFormat1).parse(s));
        }

        public String str2date(String s) {
            String dataStr = "";
            try {
                dataStr = new SimpleDateFormat(strFormat).format(Long.parseLong(s) * 1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return dataStr;
        }
    }
}
