package com.hwsafe.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;

import com.google.common.base.Strings;

public class DateUtil {

    private static final String yyMMdd = "yy-MM-dd";
    public static final String yyyyMMdd = "yyyy-MM-dd";
    private static final String yyyyMMddHHmm = "yyyy-MM-dd HH:mm";
    private static final String yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";
    private static final String yyyyMMddHHmmssSSS = "yyyyMMddHHmmssSSS";
    private static final String HHmm = "HHmm";
    public static final String yyyyMM = "yyyy-MM";
    private static final String yyyyMMdd2 = "yyyyMMdd";
    public static final String yyyyMMString = "yyyy年MM月";
    public static final String yyyyMMddString = "yyyy年MM月dd日";

    static SimpleDateFormat formatyyMMdd = new SimpleDateFormat(yyMMdd);
    static SimpleDateFormat formatyyyyMMdd = new SimpleDateFormat(yyyyMMdd);
    static SimpleDateFormat formatyyyyMMddHHmm = new SimpleDateFormat(
            yyyyMMddHHmm);
    static SimpleDateFormat formatyyyyMMddHHmmss = new SimpleDateFormat(
            yyyyMMddHHmmss);
    static SimpleDateFormat formatyyyyMMddHHmmssSSS = new SimpleDateFormat(
            yyyyMMddHHmmssSSS);
    static SimpleDateFormat formatHHmm = new SimpleDateFormat(HHmm);
    static SimpleDateFormat formatyyyyMM = new SimpleDateFormat(yyyyMM);
    static SimpleDateFormat formatyyyyMMString = new SimpleDateFormat(
            yyyyMMString);
    static SimpleDateFormat formatyyyyMMddString = new SimpleDateFormat(
            yyyyMMddString);
    static DateTime dateTime = new DateTime();
    // 时间格式
    static String[] parsePatterns = { "yyyy-MM-dd", "yyyy年MM月dd日",
            "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy/MM/dd",
            "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyyMMdd" };

    /**
     * @Title:parseDate
     * @Description TODO(字符串转为date yyyy - MM - dd HH : mm : ss).
     * @date 2015年7月6日 上午10:29:39
     * @author peijun
     * @param str
     *            字符串
     * @return Date
     * @throws ParseException
     */
    public static Date parseDate(String str) throws ParseException {
        return parseDate(str, yyyyMMddHHmmss);
    }

    /**
     * @Title:parseDate
     * @Description TODO(字符串转为date).
     * @date 2015年7月6日 上午10:29:02
     * @author peijun
     * @param str
     *            字符串
     * @param format
     *            格式
     * @return Date
     */
    public static Date parseDate(String str, String format) {
        if (null == str || "".equals(str)) {
            return null;
        }
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(str);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Calendar getCalendar() {
        return Calendar.getInstance();
    }

    public static String format_yyMMdd(Date date) {
        if (date == null) {
            return null;
        }
        return formatyyMMdd.format(date);
    }

    public static Date parse_yyMMdd(String date) {
        if (Strings.isNullOrEmpty(date)) {
            return null;
        }
        try {
            return formatyyMMdd.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String format_yyyyMMdd(Date date) {
        if (date == null) {
            return null;
        }
        return formatyyyyMMdd.format(date);
    }

    public static String format_yyyyMMString(Date date) {
        if (date == null) {
            return null;
        }
        return formatyyyyMMString.format(date);
    }

    public static Date parse_yyyyMMdd(String date) {
        if (Strings.isNullOrEmpty(date)) {
            return null;
        }
        try {
            return formatyyyyMMdd.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date parse_yyyyMM(String date) {
        if (Strings.isNullOrEmpty(date)) {
            return null;
        }
        try {
            return formatyyyyMM.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date parse_yyyyMMString(String date) {
        if (Strings.isNullOrEmpty(date)) {
            return null;
        }
        try {
            return formatyyyyMMString.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date parse_yyyyMMddString(String date) {
        if (Strings.isNullOrEmpty(date)) {
            return null;
        }
        try {
            return formatyyyyMMddString.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String format_yyyyMMddHHmmss(Date date) {
        if (date == null) {
            return null;
        }
        return formatyyyyMMddHHmmss.format(date);
    }

    public static String format_yyyyMMddHHmmssSSS(Date date) {
        if (date == null) {
            return null;
        }
        return formatyyyyMMddHHmmssSSS.format(date);
    }

    public static String format_yyyyMMddHHmm(Date date) {
        if (date == null) {
            return null;
        }
        return formatyyyyMMddHHmm.format(date);
    }

    public static Date parse_yyyyMMddHHmmss(String date) {
        if (Strings.isNullOrEmpty(date)) {
            return null;
        }
        try {
            return formatyyyyMMddHHmmss.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String get_yyyyMM() {
        return dateTime.toString(yyyyMM);
    }

    public static String get_yyMMdd() {
        return dateTime.toString(yyMMdd);
    }

    public static String get_yyyyMMdd() {
        return dateTime.toString(yyyyMMdd);
    }

    public static String get_yyyyMMddHHmm() {
        return dateTime.toString(yyyyMMddHHmm);
    }

    public static String get_yyyyMMddHHmmss() {
        return dateTime.toString(yyyyMMddHHmmss);
    }

    public static String formatDate(Date date, String format) {
        SimpleDateFormat sf = new SimpleDateFormat(format);
        return sf.format(date);
    }

    public static String getYearMonth() {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM");
        Date date = new Date();
        return sf.format(date);
    }

    public static String formatDate(String date, String format) {

        DateTime time = new DateTime(date);

        SimpleDateFormat sf = new SimpleDateFormat(format);
        return sf.format(new Date(time.getMillis()));
    }

    public static String timestampStrToString(String time) {

        try {
            Date date = new Date();
            // 注意format的格式要与日期String的格式相匹配
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            date = sdf.parse(time);
            return formatDate(date, "yyyy-MM-dd HH:mm");
        } catch (Exception e) {
            // TODO: handle exception
            return "";
        }
    }

    public static String getMonthDay() {
        SimpleDateFormat sf = new SimpleDateFormat("MM-dd");
        Date date = new Date();
        return sf.format(date);
    }

    public static Date getLastDayEnd() {

        Calendar ca = Calendar.getInstance();
        ca.setTime(new Date());
        ca.set(Calendar.HOUR_OF_DAY, 0);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        return ca.getTime();
    }

    // 获取今日的日期
    public static String getToday() {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        return sf.format(new Date());
    }

    // 获取当前时间
    public static String getCurrentTime() {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sf.format(new Date());
    }

    // 获取昨天的日期
    public static String getYesterday() {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        date.setDate(date.getDate() - 1);
        return sf.format(date);
    }

    // 获取明天的日期
    public static String getTomorrow() {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        date.setDate(date.getDate() + 1);
        return sf.format(date);
    }

    public static int getWeek() {

        Calendar c = Calendar.getInstance();
        c.setTime(new Date(System.currentTimeMillis()));
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

        return dayOfWeek;
    }

    public static Date getTodayforDate() {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return DateUtil.stringToDate(sf.format(new Date()));
    }

    public static Date getStartOfDate(Date date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(date));
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date getEndOfDate(Date date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    new SimpleDateFormat("yyyy-MM-dd 23:59:59").format(date));
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date stringToDate(String dateStr, String pattern)
            throws ParseException {
        if (dateStr == null || pattern == null)
            return null;
        return new SimpleDateFormat(pattern).parse(dateStr);
    }

    public static Date stringToDate(String dateStr) {
        if (null == dateStr) {

            return null;
        }
        DateFormat dd = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = null;
        try {
            if (dateStr.length() < 11) {

                dateStr += " 00:00:00";
            }
            date = dd.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String timestampToString(Timestamp time) {

        if (null == time) {

            return "";
        }

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");// 定义格式，不显示毫秒
        return df.format(time);
    }

    public static String timestampToString(Timestamp time, String format) {

        if (null == time) {

            return "";
        }

        SimpleDateFormat df = new SimpleDateFormat(format);// 定义格式，不显示毫秒
        return df.format(time);
    }

    public static Date timestampToDate(Timestamp time) {
        if (null == time) {

            return null;
        }
        return new Date(time.getTime());
    }

    public static int miao(Timestamp time1, Timestamp time2) {

        long a = time1.getTime();
        long b = time2.getTime();
        int c = (int) ((a - b) / 1000);
        return c;
    }

    public static String dateDiff(long startTime, long endTime) {
        // 按照传入的格式生成一个simpledateformate对象
        long nd = 1000 * 60 * 60 * 24;// 一天的毫秒数
        long nh = 1000 * 60 * 60;// 一小时的毫秒数
        long nm = 1000 * 60;// 一分钟的毫秒数
        long ns = 1000;// 一秒钟的毫秒数
        long diff;
        try {
            // 获得两个时间的毫秒时间差异
            diff = endTime - startTime;
            long day = diff / nd;// 计算差多少天
            long year = day / 365; // 计算差多少年
            long month = day / 30; // 计算差多少月
            long hour = diff % nd / nh;// 计算差多少小时
            long min = diff % nd % nh / nm;// 计算差多少分钟
            long sec = diff % nd % nh % nm / ns;// 计算差多少秒

            if (year > 0) {

                return year + "年前";
            }
            if (month > 0) {

                return month + "月前";
            } else if (day > 0) {

                return day + "天前";
            } else if (hour > 0) {

                return hour + "小时前";
            } else if (min > 0) {

                return min + "分钟前";
            } else if (sec > 0) {

                return sec + "秒前";
            }

            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String[] getCurrentMonthFistAndLastDay() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        // 获取当前月第一天：
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
        String first = format.format(c.getTime());

        // 获取当前月最后一天
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH,
                ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        String last = format.format(ca.getTime());

        return new String[] { first, last };
    }

    /**
     * 获得一周开始的时间
     * 
     * @param date
     * @return
     */
    public static Date getStartOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int week = calendar.get(Calendar.DAY_OF_WEEK) - 2;
        if (week == -1) {
            week = 6;
        }
        calendar.add(Calendar.DAY_OF_MONTH, -week);
        return calendar.getTime();
    }

    /**
     * 获得一周结束的日期
     * 
     * @param date
     * @return
     */
    public static Date getEndOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        Date startOfWeek = getStartOfWeek(date);
        calendar.setTime(startOfWeek);
        calendar.add(Calendar.DAY_OF_MONTH, 6);
        return calendar.getTime();
    }

    /**
     * 获取本月的第一天
     * 
     * @return
     */
    public static Date getStartOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 0);
        // 设置为1号，当前日期为本月第一天
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        return calendar.getTime();
    }

    /**
     * 获取本月的最后一天
     * 
     * @return
     */
    public static Date getEndOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

        return calendar.getTime();
    }

    /**
     * 获取当年的第一天
     * 
     * @param year
     * @return
     */
    public static Date getCurrYearFirst() {
        Calendar currCal = Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        return getYearFirst(currentYear);
    }

    /**
     * 获取当年的最后一天
     * 
     * @param year
     * @return
     */
    public static Date getCurrYearLast() {
        Calendar currCal = Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        return getYearLast(currentYear);
    }

    /**
     * 获取某年第一天日期
     * 
     * @param year
     *            年份
     * @return Date
     */
    public static Date getYearFirst(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        Date currYearFirst = calendar.getTime();
        return currYearFirst;
    }

    /**
     * 获取某年最后一天日期
     * 
     * @param year
     *            年份
     * @return Date
     */
    public static Date getYearLast(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date currYearLast = calendar.getTime();

        return currYearLast;
    }

    /**
     * @Title:addDays
     * @Description TODO(某一日期添加具体天数后的日期).
     * @date 2015年7月23日
     * @author lzqiangPC
     * @param date
     *            要添加天数的日期
     * @param days
     *            天数
     * @return 某一日期添加具体天数后的日期
     */
    public static Date addDays(Date date, int days) {
        Calendar calendar = getCalendar(date);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    /**
     * @Title:getCalendar
     * @Description TODO(指定日期表示的日历).
     * @date 2015年7月24日
     * @author lzqiangPC
     * @param date
     * @return 指定日期表示的日历
     */
    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    public static Date getParsePatterns(String dateStr) {
        Date date = null;
        try {
            date = DateUtils.parseDate(dateStr, parsePatterns);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date;
    }

    /**
     * @param safeOperationOpenFireGroupInfoBO
     * @return 时间间隔多少小时
     */
    public long betweenHour(Date start, Date end) {
        long hours = (start.getTime() - end.getTime()) / (1000 * 60 * 60);
        return hours;
    }

    /**
     * @param safeOperationOpenFireGroupInfoBO
     * @return 时间间隔多少天
     */
    public static long betweenDay(Date start, Date end) {
        long days = (end.getTime() - start.getTime()) / (1000 * 60 * 60 * 24);
        return days;
    }

    /**
     * 获取前/后多少分钟
     * 
     * @Title:getRangeMm
     * @Description TODO(这里用一句话描述这个方法的作用).
     * @date 2019年11月12日
     * @author Lenovo
     * @param rangemm
     * @return
     */
    public static String getRangeMm(Calendar time, int rangemm) {
//	    Calendar time = Calendar.getInstance();
        time.add(Calendar.MINUTE, rangemm);// 5分钟之前的时间
        Date nowtime = time.getTime();
        String resTime = new SimpleDateFormat(yyyyMMddHHmm).format(nowtime); // 前五分钟时间
        return resTime;
    }

    /**
     * 转换
     * 
     * @Title:getYyyyMmDdHhMm
     * @Description TODO(这里用一句话描述这个方法的作用). TODO(这里描述这个方法适用条件 – 可选).
     *              TODO(这里描述这个方法的执行流程 – 可选). TODO(这里描述这个方法的使用方法 – 可选).
     *              TODO(这里描述这个方法的注意事项 – 可选).
     * @date 2019年11月12日
     * @author Lenovo
     * @param time
     * @return
     */
    public static String getYyyyMmDdHhMm(Calendar time) {
        Date date = time.getTime();
        String resTime = new SimpleDateFormat(yyyyMMddHHmm).format(date);
        return resTime;
    }

    public static String format_HHmm(Date date) {
        if (date == null) {
            return null;
        }
        return formatHHmm.format(date);
    }

    public static String getRangeMonth(Calendar time, int rangemonth) {
        time.add(Calendar.MONTH, rangemonth);// 5分钟之前的时间
        Date nowtime = time.getTime();
        String resTime = new SimpleDateFormat(yyyyMM).format(nowtime); // 前几个月时间
        return resTime;
    }

    // 字符串yyyymm转换为yyyy-MM
    public static String formatyyyyMMDate(String date) {

        if (StringUtils.isEmpty(date)) {
            return get_yyyyMM();
        }
        if (date.length() < 6) {// 只有年的时候补到8位
            date += "0101";
        } else if (date.length() < 8) {
            date += "01";
        }
        Date time = null;
        time = parseDate(date, yyyyMMdd2);
        String resTime = new SimpleDateFormat(yyyyMM).format(time);
        return resTime;
    }

    /**
     * 获取前/后一个月日期
     * 
     * @Title:getOneMonth
     * @date 2019年12月12日
     * @author zxn
     * @param date
     * @param month
     * @return
     */
    public static String getOneMonth(DateTime date, int month) {
        if (date == null) {
            return null;
        }
        date = date.plusMonths(month);
        return date.toString(yyyyMMdd);
    }

    /**
     * 获取HHmm
     * 
     * @Title:getOneMonth
     * @date 2019年12月12日
     * @author zxn
     * @param date
     * @param month
     * @return
     * @throws ParseException
     */
    public static String getHHmm(String date) {
        String hhmm = "";
        try {
            if (date == null) {
                return null;
            }
            Calendar cal = Calendar.getInstance();
            cal.setTime(parseDate(date));
            int hour = cal.get(Calendar.HOUR_OF_DAY);// 小时
            int minute = cal.get(Calendar.MINUTE);// 分
            if (hour < 10) {
                hhmm += "0" + hour;
            } else {
                hhmm += hour;
            }
            if (minute < 10) {
                hhmm += "0" + minute;
            } else {
                hhmm += minute;
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return hhmm;
    }
}
