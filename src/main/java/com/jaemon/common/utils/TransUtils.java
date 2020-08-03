/*
 * Copyright(c) 2015-2019, Answer.AI.L
 * ShenZhen AAL Technology Co., Ltd.
 * All rights reserved.
 *
 * https://github.com/AnswerAIL/
 */
package com.jaemon.common.utils;

import com.jaemon.common.utils.exception.AALException;

/**
 * <p>
 *     转换工具类
 * </p>
 *
 * @author Answer.AI.L
 * @date 2019-05-14
 */
public class TransUtils {

    /**
     * 将 excel 中的字母列索引转换为数字列索引
     *
     * @param letterColIdx 字母列索引
     * @return rlt 返回字母列索引对应的数字索引 A -> 0  BA -> 52 CA -> 78
     * */
    public static long transLetter2Num(String letterColIdx) {
        long rlt = 0;
        letterColIdx = letterColIdx.toUpperCase();

        String[] sts = letterColIdx.split("");

        int length = sts.length;
        for (int i = 0; i < length; i++) {
            String st = sts[i];
            char[] cs = st.toCharArray();

            int idx = (int)cs[0];

            // 如果字符不是 A-Z 之间的字符, 则抛错
            if (idx < 65 || idx > 90) {
                throw new AALException("Characters["+ st +"] not between A and Z.");
            }

            idx -= 64;
            long num = (long) (idx * Math.pow(26, length - i - 1));
//            System.out.println("i: " + i + ", num: " + num);
            rlt  += num;
        }

        return --rlt;
    }



    public static void main(String[] args) {
        System.out.println("A: " + transLetter2Num("A"));
        System.out.println("BA: " + transLetter2Num("BA"));
        System.out.println("CA: " + transLetter2Num("CA"));
        System.out.println("CAZ: " + transLetter2Num("CAZ"));
        System.out.println("CBA: " + transLetter2Num("CBA"));
    }


}