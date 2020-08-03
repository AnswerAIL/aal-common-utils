/*
 * Copyright(c) 2015-2019, Answer.AI.L
 * ShenZhen AAL Technology Co., Ltd.
 * All rights reserved.
 *
 * https://github.com/AnswerAIL/
 */
package com.jaemon.common.utils.date;

import com.google.common.collect.Maps;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Map;

/**
 * <p>
 *     日期转换指定格式
 * </p>
 *
 * @author Answer.AI.L
 * @version 1.0
 * @date 2019-08-27
 */
//@Slf4j
public class Date2FmtUtils {

    private static final Map<String, String> VARIS = Maps.newHashMap();
    static {
        VARIS.put("yyyy", "2019");
        VARIS.put("MM", "01");
        VARIS.put("dd", "01");
        VARIS.put("HH", "00");
        VARIS.put("mm", "00");
        VARIS.put("ss", "00");
        VARIS.put("SSS", "001");
    }

    public static void main(String[] args) {

//        String format = "yyyy-MM-dd HH:mm:ss";
        System.out.println("1=>" + toDateStr("2019", "yyyy-MM-dd HH:mm:ss"));
        System.out.println("2=>" + toDateStr("2019-08", "yyyy-MM-dd HH:mm:ss"));
        System.out.println("3=>" + toDateStr("2019-08-01", "yyyy-MM"));
        System.out.println("4=>" + toDateStr("2019-08-01", "yyyy"));
        System.out.println("5=>" + toDateStr("2019-08-01 01", "yyyy-MM-dd HH:mm:ss"));
        System.out.println("6=>" + toDateStr("2019080101", "yyyy-MM-dd HH:mm:ss"));
        System.out.println("7=>" + toDateStr("201908", "yyyy-MM-dd HH:mm:ss.SSS"));
        System.out.println("8=>" + toDateStr("2019080101", "yyyy-MM-dd HH:mm:ss.SSS"));
        System.out.println("9=>" + toDateStr("2019-08-29T00:00:00+08:00", "yyyy-MM-dd HH:mm:ss.SSS"));
        System.out.println("10=>" + toDateStr("2019年08月29日T00时00:00", "yyyy-MM-dd HH:mm:ss"));
        System.out.println("11=>" + toDateStr("Thu Aug 29 15:54:50 CST 2019", "yyyy-MM-dd HH:mm:ss"));
    }


    /**
     * 日期转换指定格式
     *
     * @param date 日期
     * @param format 格式, eg: yyyy-MM-dd HH:mm:ss
     * @return date
     */
    public static String toDateStr(String date, String format) {
        date = date.endsWith("+08:00") ? date.replaceAll("\\+08\\:00", "") : date;
        date = date.replaceAll("[年月]", "-").replaceAll("[时分]", ":").replaceAll("[日号秒]", "");
//        log.info("转换前 date=[{}], format=[{}]", date, format);
        if (date.length() > 23 || format.length() > 23) {
            try {
                LocalDateTime localDateTime = LocalDateTime.ofInstant(new Date(date).toInstant(), ZoneId.systemDefault());
                return localDateTime.format(DateTimeFormatter.ofPattern(format));
            } catch (Exception e) {
                e.printStackTrace();
//                log.warn("日期及日期格式长度最大不能超过23, date=[{}], format=[{}]", date, format);
                return null;
            }
        }
        String rlt;

        if (date.length() != format.length()) {
            String ft = format.replaceAll("[- :.]", "");
            if (format.length() < date.length()) {
                date = date.substring(0, format.length());
            }
            date = date.replaceAll("[- :.T]", "");

            date = date + ft.substring(date.length(), ft.length());
            for (String key: VARIS.keySet()) {
                String value = VARIS.get(key);
                date = date.replaceAll(key, value);
            }
            rlt = date2Fmt(date, format);
        } else {
            rlt = date;
        }

//        log.info("转换后 date=[{}], format=[{}]", rlt, format);
        return rlt;
    }

    /**
     * 日期转 format 格式
     *
     * @param date 日期
     * @param format 日期格式
     * @return rlt
     */
    private static String date2Fmt(String date, String format) {
        StringBuilder sb = new StringBuilder();
        String[] as = format.split("");
        for (int i = 0, j = 0; i < as.length; i++) {
            String e = as[i];
            if ("-".equals(e) || ":".equals(e) || ".".equals(e)) {
                sb.append(e);
            } else if (" ".equals(e)) {
                sb.append("T");
            } else {
                sb.append(date.substring(j, j + 1));
                j++;
            }
        }
        return sb.toString();
    }



    /**
     * 日期是否在 year 年内
     *
     * @param date 日期
     * @param format 日期格式
     * @param years 年数
     * @return boolean
     */
    private static boolean isDate(String date, String format, long years) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        LocalDate startDate = LocalDate.now().minusYears(years).withMonth(1).withDayOfMonth(1);

        String fmtDate;
        try {
            fmtDate = LocalDate.parse(date).format(formatter);
        } catch (DateTimeParseException e) {
//            log.warn("日期=[{}]转格式=[{}]异常, 准备进行适配转换, ex msg=[{}]", date, format, e.getMessage());
            fmtDate = toDateStr(date, format);
        }

        if (fmtDate != null) {
            LocalDate parse = LocalDate.parse(fmtDate, formatter);
            return parse.isAfter(startDate);
        }
        return false;
    }


    /**
     * 日期时间是否在 year 年内
     *
     * @param date 日期
     * @param format 日期格式
     * @param years 年数
     * @return boolean
     */
    private static boolean isDateTime(String date, String format, long years) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        LocalDateTime startDate = LocalDateTime.now().minusYears(years).withMonth(1).withDayOfMonth(1);

        String fmtDate;
        try {
            fmtDate = LocalDateTime.parse(date).format(formatter);
        } catch (DateTimeParseException e) {
//            log.warn("日期=[{}]转格式=[{}]异常, 准备进行适配转换, ex msg=[{}]", date, format, e.getMessage());
            fmtDate = toDateStr(date, format);
        }

        if (fmtDate != null) {
            LocalDateTime parse = LocalDateTime.parse(fmtDate, formatter);
            return parse.isAfter(startDate);
        }
        return false;
    }
}