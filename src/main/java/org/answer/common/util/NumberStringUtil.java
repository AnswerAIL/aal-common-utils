/*
 * Copyright(c) 2015-2019, Answer.AI.L
 * ShenZhen AAL Technology Co., Ltd.
 * All rights reserved.
 *
 * https://github.com/AnswerAIL/
 */
package org.answer.common.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * <p>
 *     字符工具类
 * </p>
 *
 * @author Answer.AI.L
 * @date 2019-03-07
 */
public class NumberStringUtil {

    public NumberStringUtil() {
    }


    /**
     * 转换字节数组为16进制字符串
     *
     * @param bytes byte[]
     * @return hex string
     */
    public static String bytesToHexString(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (bytes == null || bytes.length <= 0) {
            return "";
        }
        for (byte b: bytes) {
            int v = b & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString().toUpperCase();
    }


    /**
     * 16进制字符串转字节数组
     *
     * @param hexString hex string
     * @return byte[]
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || "".equals(hexString)) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }


    /**
     * 字符转字节
     *
     * @param c char
     * @return byte
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }


    /**
     * 左补零
     * @param str .
     * @param length .
     * @return str
     */
    public static String addLeftZero(String str, int length) {
        int strLength = str.length();
        StringBuilder sb = new StringBuilder(str);
        for (int i = 0; i < (length - strLength); i = i + 1) {
            sb.insert(0, '0');
        }
        return sb.toString();
    }


    /**
     * 左补字符,按字节长度计算
     *
     * @param str .
     * @param length .
     * @return str
     */
    public static String addLeftChar(String str, int length, char c, String encode) {
        if (str == null) {
            str = "";
        }
        StringBuilder sb = new StringBuilder(str);
        int strLength = 0;
        try {
            strLength = str.getBytes(encode).length;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < (length - strLength); i = i + 1) {
            sb.insert(0, c);
        }
        return sb.toString();
    }


    /**
     * 右补字符,按字节长度计算
     * @param str .
     * @param length .
     * @return str
     */
    public static String addRightChar(String str, int length, char c, String encode) {
        str = (str == null) ? "" : str;

        StringBuilder sb = new StringBuilder(str);

        int strLength = 0;
        try {
            strLength = str.getBytes(encode).length;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < (length - strLength); i = i + 1) {
            sb.append(c);
        }
        return sb.toString();
    }


    /**
     * 获取特定字节长度的字符串
     *
     * @param str .
     * @param length .
     * @return str
     */
    public static String getByteLengthStr(String str, int length, String encode) {
        if (str == null) {
            str = "";
        }
        int strLength = 0;
        try {
            strLength = str.getBytes(encode).length;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (strLength <= length) {
            return str;
        } else {
            byte[] byteTmp = new byte[length];
            String strTmp = "";
            try {
                byte[] strByte = str.getBytes(encode);

                System.arraycopy(strByte, 0, byteTmp, 0, length);
                strTmp = new String(byteTmp, encode);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return strTmp;
        }
    }


    /**
     * 字节数据相加
     *
     * @param sByte 源字节数组
     * @param aByte 待新增数组
     * @return byte[]
     */
    public static byte[] byteArrayAdd(byte[] sByte, byte[] aByte) {
        byte[] tByte = new byte[sByte.length + aByte.length];
        System.arraycopy(sByte, 0, tByte, 0, sByte.length);
        System.arraycopy(aByte, 0, tByte, sByte.length, aByte.length);
        return tByte;
    }


    /**
     * 在字节数组获取，从第index开始，length长度的字节数
     * @param byteArray .
     * @param index     从0开始
     * @param length .
     * @return byte[]
     */
    public static byte[] getBytes(byte[] byteArray, int index, int length) {
        byte[] getByteArray = new byte[length];

        System.arraycopy(byteArray, index, getByteArray, 0, length);


        return getByteArray;
    }


    /**
     * 字节转换二进制
     * @param byteArray 字节数组
     * @return str
     */
    public static String byteToBinary(byte[] byteArray) {
        StringBuilder sb = new StringBuilder();
        for (byte b: byteArray) {
            int z = b;
            z |= 256;
            String str = Integer.toBinaryString(z);
            int len = str.length();
            sb.append(str.substring(len - 8, len));
        }
        return sb.toString();
    }


    /**
     * 文件16进制字符串转文件流
     *
     * @param hexString 十六进制字符串
     * @return inputStream
     * */
    public static InputStream hexToInputStream(String hexString) {
        byte[] bytes = hexStringToBytes(hexString);

        return new ByteArrayInputStream(bytes);
    }


}
