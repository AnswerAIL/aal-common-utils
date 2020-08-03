/*
 * Copyright(c) 2015-2019, Answer.AI.L
 * ShenZhen AAL Technology Co., Ltd.
 * All rights reserved.
 *
 * https://github.com/AnswerAIL/
 */
package com.jaemon.common.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;

/**
 * <p>
 *     图片操作工具类
 * </p>
 *
 * <ul>
 *     <li>
 *         Java Api: http://tool.oschina.net/uploads/apidocs/jdk-zh/
 *     </li>
 *     <li>
 *         图片工具包可使用 Thumbnails 包
 *     </li>
 * </ul>
 *
 * @author Answer.AI.L
 * @version 1.0
 * @date 2019-09-27
 */
public class ImageUtils {

    private ImageUtils() {}


    /**
     * 图片内容移动
     *
     * @param src 源图片文件
     * @param descPath 处理后图片路径
     * @param shift 偏移方向 {@link ShiftEnum}
     * @param offset 偏移量
     * @throws IOException io
     */
    public static void shift(File src, String descPath, ShiftEnum shift, int offset) throws IOException {
        String fileName = src.getName();
        String[] fileNameArr = src.getName().split("\\.");
        if (fileNameArr.length != 2) {
            throw new IllegalArgumentException("原图片名称格式异常, 没有图片名称后缀名");
        }
        File desc = new File(descPath + File.separator + fileName);
        shift(src, desc, shift, offset, fileNameArr[1]);
    }

    /**
     * 图片内容移动
     *
     * @param src 源图片文件
     * @param desc 处理后图片文件
     * @param shift 偏移方向 {@link ShiftEnum}
     * @param offset 偏移量
     * @throws IOException io
     */
    public static void shift(File src, File desc, ShiftEnum shift, int offset) throws IOException {
        String[] fileNameArr = src.getName().split("\\.");
        if (fileNameArr.length != 2) {
            throw new IllegalArgumentException("原图片名称格式异常, 没有图片名称后缀名");
        }
        shift(src, desc, shift, offset, fileNameArr[1]);
    }


    /**
     * 图片内容移动
     *
     * @param src 源图片文件
     * @param desc 处理后图片文件
     * @param shift 偏移方向 {@link ShiftEnum}
     * @param offset 偏移量
     * @param formatName 图片格式名称
     * @throws IOException io
     */
    public static void shift(File src, File desc, ShiftEnum shift, int offset, String formatName) throws IOException {
        BufferedImage srcImg = ImageIO.read(src);
        int width = srcImg.getWidth();
        int height = srcImg.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        Graphics graphics = image.createGraphics();
        int x = 0, y = 0;
        // 设置填充颜色
        graphics.setColor(srcImg.getGraphics().getColor());
        // 填充指定的矩形
        graphics.fillRect( x, y, width, height);
        switch (shift) {
            case UP: y -= offset; break;
            case DOWN: y += offset; break;
            case LEFT: x -= offset; break;
            case RIGHT: x -= offset; break;
            default: throw new IllegalArgumentException(MessageFormat.format("shift={} is invalid.", shift));
        }
        graphics.drawImage(srcImg, x, y, width, height, null);
        // 释放此图形的上下文以及它使用的所有系统资源
        graphics.dispose();
        ImageIO.write(image, formatName, desc);
    }


    /**
     * 偏移方位枚举
     * */
    public enum ShiftEnum {
        LEFT, RIGHT, UP, DOWN
    }


    /**
     * 添加水印
     *
     * @param src 源图片文件
     * @param desc 处理后图片文件
     * @param waterMarkTxt 水印内容
     * @param fontSize 水印字体大小
     * @throws IOException io
     */
    public static void waterMark(File src, File desc, String waterMarkTxt, int fontSize) throws IOException {
        String[] fileNameArr = src.getName().split("\\.");
        if (fileNameArr.length != 2) {
            throw new IllegalArgumentException("原图片名称格式异常, 没有图片名称后缀名");
        }
        BufferedImage srcImg = ImageIO.read(src);
        int width = srcImg.getWidth();
        int height = srcImg.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        Graphics graphics = image.createGraphics();
        int x = 0, y = 0;
        // 设置水印字体颜色
        graphics.setColor(new Color(223, 223, 223));
        // 设置水印字体信息
        graphics.setFont(new Font(Font.MONOSPACED, Font.ITALIC, fontSize));
        // 设置水印的位置
        graphics.drawString(waterMarkTxt, (width - length(waterMarkTxt) * fontSize) / 2, height / 2);
        graphics.drawImage(srcImg, x, y, width, height, null);
        // 释放此图形的上下文以及它使用的所有系统资源
        graphics.dispose();
        String formatName = fileNameArr[1];
        ImageIO.write(image, formatName, desc);
    }

    private static int length(String txt) {
        int length = 0;
        for (int i = 0; i < txt.length(); i++) {
            if ((Character.toString(txt.charAt(i))).getBytes().length > 1) {
                length += 2;
            } else {
                length += 1;
            }
        }
        return length / 2;
    }


    /**
     * 调整图片尺寸
     * @param src 源图片文件
     * @param desc 处理后图片文件
     * @param percent 调整百分比
     * @throws IOException io
     */
    public static void resize(File src, File desc, double percent) throws IOException {
        String[] fileNameArr = src.getName().split("\\.");
        if (fileNameArr.length != 2) {
            throw new IllegalArgumentException("原图片名称格式异常, 没有图片名称后缀名");
        }
        BufferedImage srcImg = ImageIO.read(src);
        int x = 0, y = 0;
        int width = srcImg.getWidth();
        int height = srcImg.getHeight();
        int newWidth = (width * (int)(percent * 100)) / 100;
        int newHeight = (height * (int)(percent * 100)) / 100;
        BufferedImage image = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_BGR);
        Graphics graphics = image.createGraphics();
        graphics.drawImage(srcImg, x, y, newWidth, newHeight, null);
        ImageIO.write(image, fileNameArr[1], desc);
    }


    /**
     * 截图(待优化)
     *
     * @param srcFile  原图片
     * @throws Exception .
     */
    private static void screenshot(File srcFile) throws Exception {
        BufferedImage srcImg = ImageIO.read(srcFile);
        // 新图片的高度和宽度
        int width = srcImg.getWidth() / 5, height = srcImg.getHeight() / 10;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        Graphics graphics = image.createGraphics();
        // 设置填充颜色
        graphics.setColor(srcImg.getGraphics().getColor());
        // 填充区域
        graphics.fillRect( 0, 0, width, height);
        // 画图
        graphics.drawImage(srcImg, 90, 35, srcImg.getWidth(), srcImg.getHeight(), null);
        graphics.setPaintMode();
        graphics.dispose();
        ImageIO.write(image, "jpg", srcFile);
    }


    public static void main(String[] args) {
        // 调整图片尺寸
        try {
            File srcFile = new File("/home/answer/src/0_001.jpg");
            File descFile = new File("/home/answer/desc/0001.jpg");
            resize(srcFile, descFile, 2.5);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 添加水印
        /*try {
            File srcFile = new File("/home/answer/src/0_001.jpg");
            File descFile = new File("/home/answer/desc/0001.jpg");
            waterMark(srcFile, descFile, "AnswerAIL", 30);
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        // 图片内容移动
        /*try {
            File srcFile = new File("/home/answer/src/0_001.jpg");
            File descFile = new File("/home/answer/desc/0001.jpg");
            shift(srcFile, descFile, ShiftEnum.LEFT, 5);
        } catch (Exception e) {
            e.printStackTrace();
        }*/

    }
}

