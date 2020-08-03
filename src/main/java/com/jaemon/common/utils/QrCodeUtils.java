/*
 * Copyright(c) 2015-2019, Answer.AI.L
 * ShenZhen AAL Technology Co., Ltd.
 * All rights reserved.
 *
 * https://github.com/AnswerAIL/
 */
package com.jaemon.common.utils;

import com.google.common.collect.Maps;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * <p>
 *     二维码工具类
 * </p>
 *
 * @author L.Jaemon
 * @date 2020-01-02
 */
@SuppressWarnings("unchecked")
public class QrCodeUtils {

    private QrCodeUtils() {}

    /**
     * 生成二维码
     *
     * @param file      生成的二维码文件
     * @param content   二维码内容
     * @param format    图片格式
     * @throws IOException .
     * @throws WriterException .
     */
    public static void encode(File file, String content, String format) throws IOException, WriterException {
        // 设置生成的二维码的高度和宽度
        int height = 500, width = 500;
        // 编码时的额外参数
        Map hints = Maps.newHashMap();
        // 编码
        hints.put(EncodeHintType.CHARACTER_SET, StandardCharsets.UTF_8.displayName());
        // 容错等级
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        // 边框
        hints.put(EncodeHintType.MARGIN, 2);
        // 编码的方式（二维码、条形码...）
        BarcodeFormat qrCode = BarcodeFormat.QR_CODE;

        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, qrCode, width, height, hints);
        MatrixToImageWriter.writeToPath(bitMatrix, format, file.toPath());
    }

    /**
     * 生成带有logo图片的二维码
     *
     * @param file      生成的二维码文件
     * @param content   二维码内容
     * @param logoImg   二维码logo图片
     * @throws IOException .
     * @throws WriterException .
     */
    public static void encode(File file, String content, File logoImg) throws IOException, WriterException {
        String fileName = file.getName();
        String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
        encode(file, content, fileType);
        BufferedImage logo = ImageIO.read(file);
        overlapImage(logo, fileType, file, logoImg);
    }

    /**
     * 识别二维码
     *
     * @param file 二维码文件内容
     * @throws IOException .
     * @throws NotFoundException 未找到有效二维码
     * @return 识别结果
     */
    public static String decode(File file) throws IOException, NotFoundException {
        MultiFormatReader formatReader = new MultiFormatReader();
        BufferedImage image = ImageIO.read(file);
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));
        Map hints = Maps.newHashMap();
        hints.put(EncodeHintType.CHARACTER_SET, StandardCharsets.UTF_8.displayName());
        Result result = formatReader.decode(binaryBitmap, hints);
        // 种类： result.getBarcodeFormat()
        return result.getText();
    }


    /**
     * 将logo添加到二维码中间
     *
     * @param image     生成的二维码照片对象
     * @param imageFile 二维码文件
     * @param logoFile  logo文件
     * @param formatName 照片格式
     */
    private static void overlapImage(BufferedImage image, String formatName, File imageFile, File logoFile) {
        try {
            BufferedImage logo = ImageIO.read(logoFile);
            Graphics2D graphics = image.createGraphics();

            // 设置 logo 图片的高度和宽度
            int height = image.getHeight() / 6, width = image.getWidth() / 6;
            // logo居中显示
            int x = (image.getWidth() - width) / 2, y = (image.getHeight() - height) / 2;

            // 绘制图
            graphics.drawImage(logo, x, y, width, height, null);

            // 设置 logo 边框
            graphics.setStroke(new BasicStroke(3.0f));
            // 设置 logo 边框颜色
            graphics.setColor(Color.orange);
            // 画边框
            graphics.drawRect(x, y, width, height);
            graphics.dispose();

            // 写入logo照片到二维码
            ImageIO.write(image, formatName, imageFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) throws Exception {
        File file = new File("src/test/resources/qrcode.jpg");
        String content = "这是一个二维码";

        encode(file, content, "jpg");

        String text = decode(file);
        System.out.println("识别结果: " + text);

        encode(new File("src/test/resources/qrcode_logo.jpg"), content, new File("src/test/resources/logo.gif"));
    }
}