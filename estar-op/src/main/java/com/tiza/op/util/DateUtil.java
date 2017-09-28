package com.tiza.op.util;

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
        if (null != datetime && datetime.trim().length() == 19) {
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

    public static Date str2Date(String date){
        DateFormat format = new SimpleDateFormat("yyyyMMdd");

        Date d = null;
        if (null != date && date.trim().length() == 8) {
            try {

                d = format.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return d;

    }

    public static String date2Str(Date date){
        if (date == null) {

            return null;
        }

        return String.format("%1$tY%1$tm%1$td", date);
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

    public static int getTableYM(Date date){
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        //calendar.add(calendar.DATE, -1);


        return getYM(calendar);
    }
}
