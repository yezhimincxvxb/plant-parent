package com.moguying.plant.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public enum DateUtil {
    INSTANCE;

    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public String orderNumberWithDate() {
        return DateFormatUtils.format(new Date(), "yyyyMMddHHmmsss").concat(RandomStringUtils.randomNumeric(6));
    }

    /**
     * 格式化时分秒
     */
    public String numberWithDate() {
        return DateFormatUtils.format(new Date(), "HHmmsss");
    }

    public String formatDateForPayment(Date now, String format) {
        return DateFormatUtils.format(now, format);
    }

    public String formatDateForPayment(Date now) {
        return DateFormatUtils.format(now, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 本月第一天
     */
    public Date firstDayOfMonth() {
        LocalDate localDate = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
        try {
            return DATE_FORMAT.parse(localDate.toString());
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 下个月第一天
     */
    public Date fistDayOfNextMonth() {
        LocalDate localDate = LocalDate.now().with(TemporalAdjusters.firstDayOfNextMonth());
        try {
            return DATE_FORMAT.parse(localDate.toString());
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 本月最后一天
     */
    public Date lastDayOfMonth() {
        LocalDate localDate = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
        try {
            return DATE_FORMAT.parse(localDate.toString());
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 下个月最后一天
     */
    public Date lastDayOfNextMonth() {
        LocalDateTime localDateTime = LocalDateTime.now().with(TemporalAdjusters.firstDayOfNextMonth());
        LocalDate localDate = LocalDate.of(localDateTime.getYear(), localDateTime.getMonth(), localDateTime.getDayOfMonth())
                .with(TemporalAdjusters.lastDayOfMonth());
        try {
            return DATE_FORMAT.parse(localDate.toString());
        } catch (ParseException e) {
            return null;
        }
    }


    public Date todayBegin(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(date);
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }

    public Date todayBegin() {
        return todayBegin(new Date());
    }


    public Date todayEnd(Date date) {
        int dayMillisecond = 24 * 60 * 60 * 1000;
        return new Date(todayBegin(date).getTime() + (dayMillisecond - 1000));
    }

    public Date todayEnd() {
        return todayEnd(new Date());
    }

    public Date dateBegin(Date date, int days) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return todayBegin(calendar.getTime());
    }

    public Date dateEnd(Date date, int days) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return todayEnd(calendar.getTime());
    }

    /**
     * 当前时间是否在某段时间内
     */
    public Boolean betweenTime(Date begin, Date end, Date now) {
        Calendar cBegin = Calendar.getInstance();
        cBegin.setTime(begin);

        Calendar cEnd = Calendar.getInstance();
        cEnd.setTime(end);

        Calendar cNow = Calendar.getInstance();
        cNow.setTime(now);
        return cNow.after(cBegin) && cNow.before(cEnd);
    }

    public Boolean betweenTime(Date begin, Date end) {
        return betweenTime(begin, end, new Date());
    }

    public Boolean betweenTime(Date begin) {
        Date end = nextDay(begin);
        return betweenTime(begin, end, new Date());
    }

    /**
     * 下一天时间
     */
    public Date nextDay(Date begin) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(begin);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 毫秒值转时分秒
     */
    public String longToString(long millis) {
        int ss = 1000;
        int mi = ss * 60;
        int hh = mi * 60;

        long hour = millis / hh;
        long minute = (millis - hour * hh) / mi;
        long second = (millis - hour * hh - mi * minute) / ss;

        return getString(hour) + ":" + getString(minute) + ":" + getString(second);
    }

    /**
     * 时分秒小于9前面补零
     */
    public String getString(long val) {
        return val > 9 ? "" + val : "0" + val;
    }

    /**
     * 时间字符串转化成Date类型
     */
    public Date stringToDate(String string) {
        try {
            return DATE_TIME_FORMAT.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {

    }
}
