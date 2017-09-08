package com.tiza.process.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Description: DateUtil
 * Author: DIYILIU
 * Update: 2016-03-21 16:03
 */
public class DateUtil {

    public static Date stringToDate(String datetime) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date = null;
        if (!CommonUtil.isEmpty(datetime)) {
            try {

                date = format.parse(datetime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return date;
    }

    public static String dateToString(Date date) {

        if (date == null) {

            return null;
        }

        return String.format("%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS", date);
    }

    public static String dateToString(Date date, String format){

        if (date == null) {

            return null;
        }

        return String.format(format, date);
    }

    public static String getDate(byte b, byte b1, byte b2) {
        return String.format("%02d-%02d-%02d", b, b1, b2);
    }

    public static String getTimeStr(byte b, byte b1, byte b2) {
        return String.format("%02d:%02d:%02d", b, b1, b2);
    }

    public static Long getTimeMillis(String time) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return df.parse(time).getTime();
    }

    /**
     * 日历转换成数字
     *
     * @param cal
     * @return yyyyMM
     */
    public  static  int getYM(Calendar cal) {
        int y = cal.get(Calendar.YEAR);
        int m = cal.get(Calendar.MONTH) + 1;
        return y * 100 + m;
    }

    public static int getTableYm(Date date){
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, -1);// 把日期往后增加一天.整数往后推,负数往前移动


        return getYM(calendar);
    }
}
