/*
 * Copyright(c) 2015-2019, Answer.AI.L
 * ShenZhen AAL Technology Co., Ltd.
 * All rights reserved.
 *
 * https://github.com/AnswerAIL/
 */
package org.answer.common.util;

import org.apache.commons.io.FileUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

/**
 * <p>
 *     Base64 工具
 * </p>
 *
 * https://www.runoob.com/java/java8-base64.html
 *
 * @author Answer.AI.L
 * @version 1.0
 * @date 2019-10-12
 */
public class Base64Utils {
    private Base64Utils() {
    }

    /**
     * 图片转 Base64 编码
     *
     * @param srcFile 待编码图片
     * @return result
     */
    public static String encoder(File srcFile) {
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(file2Bytes(srcFile));
    }

    /**
     * 还原 base64 解码为图片
     *
     * @param text 待解码 Base64 字符串
     * @param descFile 解码的图片路径
     */
    public static void decoder(String text, File descFile) {
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] bytes = decoder.decodeBuffer(text);
            bytes2File(bytes, descFile);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    public static String j8Encoder(File srcFile) {
        return Base64.getEncoder().encodeToString(file2Bytes(srcFile));
    }

    public static String j8MimeEncoder(File srcFile) {
        return Base64.getMimeEncoder().encodeToString(file2Bytes(srcFile));
    }

    public static String j8UrlEncoder(File srcFile) {
        return Base64.getUrlEncoder().encodeToString(file2Bytes(srcFile));
    }


    public static void j8Decoder(String text, File descFile) {
        byte[] bytes = Base64.getDecoder().decode(text);
        bytes2File(bytes, descFile);
    }

    public static void j8MimeDecoder(String text, File descFile) {
        byte[] bytes = Base64.getMimeDecoder().decode(text);
        bytes2File(bytes, descFile);
    }

    public static void j8UrlDecoder(String text, File descFile) {
        byte[] bytes = Base64.getUrlDecoder().decode(text);
        bytes2File(bytes, descFile);
    }

    private static byte[] file2Bytes(File srcFile) {
        byte[] bytes;
        try {
            bytes = FileUtils.readFileToByteArray(srcFile);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return bytes;
    }

    private static void bytes2File(byte[] bytes, File descFile) {
        try {
            FileUtils.writeByteArrayToFile(descFile, bytes);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}