/*
 * Copyright(c) 2015-2019, Answer.AI.L
 * ShenZhen AAL Technology Co., Ltd.
 * All rights reserved.
 *
 * https://github.com/AnswerAIL/
 */
package org.answer.common.util.date;

import com.google.common.collect.Maps;

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

    public static void main(String[] args) {

//        String format = "yyyy-MM-dd HH:mm:ss";
        System.out.println("1=>" + toDateStr("2019", "yyyy-MM-dd HH:mm:ss"));
        System.out.println("2=>" + toDateStr("2019-08", "yyyy-MM-dd HH:mm:ss"));
        System.out.println("3=>" + toDateStr("2019-08-01", "yyyy-MM"));
        System.out.println("4=>" + toDateStr("2019-08-01", "yyyy"));
        System.out.println("5=>" + toDateStr("2019-08-01 01", "yyyy-MM-dd HH:mm:ss"));
        System.out.println("6=>" + toDateStr("2019080101", "yyyy-MM-dd HH:mm:ss"));
        System.out.println("7=>" + toDateStr("201908", "yyyy-MM-dd HH:mm:ss"));
    }


    /**
     * 日期转换指定格式
     *
     * @param date 日期
     * @param format 格式, eg: yyyy-MM-dd HH:mm:ss
     * @return date
     */
    public static String toDateStr(String date, String format) {
//        log.info("转换前 date=[{}], format=[{}]", date, format);
        if (date.length() > 19 || format.length() > 19) {
//            log.warn("日期及日期格式长度最大不能超过19, date=[{}], format=[{}]", date, format);
            return null;
        }
        String rlt;

        Map<String, String> varis = Maps.newHashMap();
        varis.put("yyyy", "2019");varis.put("MM", "01");varis.put("dd", "01");
        varis.put("HH", "00");varis.put("mm", "00");varis.put("ss", "00");

        if (date.length() != format.length()) {
            String ft = format.replaceAll("[- :]", "");
            if (format.length() < date.length()) {
                date = date.substring(0, format.length());
            }
            date = date.replaceAll("[- :]", "");

            date = date + ft.substring(date.length(), ft.length());
            for (String key: varis.keySet()) {
                String value = varis.get(key);
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
            if ("-".equals(e) || ":".equals(e) || " ".equals(e)) {
                sb.append(e);
            } else {
                sb.append(date.substring(j, j + 1));
                j++;
            }
        }
        return sb.toString();
    }


}