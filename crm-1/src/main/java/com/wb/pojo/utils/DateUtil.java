package com.wb.pojo.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 对日期进行格式化的工具类
 */
public class DateUtil {

    public static String formatDateTime(Date date){
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sd.format(date);
    }

    public static String formatDate(Date date){
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        return sd.format(date);
    }

    public static String formatTime(Date date){
        SimpleDateFormat sd = new SimpleDateFormat("HH:mm:ss");
        return sd.format(date);
    }
}
