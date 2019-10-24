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

public enum  DateUtil {
    INSTANCE;


    public String orderNumberWithDate(){
        return DateFormatUtils.format(new Date(),"yyyyMMddHHmmsss").concat(RandomStringUtils.randomNumeric(6));
    }


    public String formatDateForPayment(Date now,String format){
        return DateFormatUtils.format(now,format);
    }

    public String formatDateForPayment(Date now){
        return DateFormatUtils.format(now,"yyyy-MM-dd HH:mm:ss");
    }


    public Date firstDayOfMonth(){
        LocalDate localDate = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
           return simpleDateFormat.parse(localDate.toString());
        } catch (ParseException e) {
            return null;
        }
    }

    public Date fistDayOfNextMonth(){
        LocalDate localDate = LocalDate.now().with(TemporalAdjusters.firstDayOfNextMonth());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return simpleDateFormat.parse(localDate.toString());
        } catch (ParseException e) {
            return null;
        }
    }


    public Date lastDayOfMonth(){
        LocalDate localDate = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(localDate.toString());
        } catch (ParseException e) {
            return null;
        }
    }

    public Date lastDayOfNextMonth(){
        LocalDateTime localDateTime = LocalDateTime.now().with(TemporalAdjusters.firstDayOfNextMonth());
        LocalDate localDate = LocalDate.of(localDateTime.getYear(),localDateTime.getMonth(),localDateTime.getDayOfMonth())
                .with(TemporalAdjusters.lastDayOfMonth());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(localDate.toString());
        } catch (ParseException e) {
            return null;
        }
    }



    public Date todayBegin(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(date);
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }

    public Date todayBegin(){
        return todayBegin(new Date());
    }


    public Date todayEnd(Date date){
       int dayMillisecond = 24 * 60 * 60 * 1000;
       return new Date(todayBegin(date).getTime() + (dayMillisecond - 1000));
    }

    public Date todayEnd(){
        return todayEnd(new Date());
    }

    public Date dateBegin(Date date,int days){
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE,days);
        return todayBegin(calendar.getTime());
    }

    public Date dateEnd(Date date ,int days){
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE,days);
        return todayEnd(calendar.getTime());
    }

    public static void main(String[] args) {
        System.out.println(DateUtil.INSTANCE.todayEnd(DateUtil.INSTANCE.lastDayOfNextMonth()));
    }
}
