/*
 * Copyright(c) 2015-2019, Answer.AI.L
 * ShenZhen AAL Technology Co., Ltd.
 * All rights reserved.
 *
 * https://github.com/AnswerAIL/
 */
package org.answer.common.util;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 *     PDF 文件转图片工具类
 * </p>
 *
 * @author Answer.AI.L
 * @version 1.0
 * @date 2019-10-09
 */
public class PdfUtils {
    private static final String IMAGE_SUFFIX = ".jpg";
    private static final String FORMAT_NAME = "jpg";

    private PdfUtils() {}


    /**
     * PDF 文件转多张图片(按页转)
     *
     * @param filePath pdf 文件路径
     * @return list
     */
    public static List<String> pdf2Images(String filePath){
        // 获取去除后缀的文件路径
        String descPath = filePath.substring(0, filePath.lastIndexOf("."));

        return pdf2Images(filePath, descPath);
    }

    /**
     * PDF 文件转多张图片(按页转)
     *
     * @param filePath pdf 文件路径
     * @param descPath 转换后图片存储路径
     * @return list
     */
    public static List<String> pdf2Images(String filePath, String descPath){
        List<String> list = new ArrayList<>();

        String imagePath;
        File file = new File(filePath);
        try {
            File descFile = new File(descPath);
            if(!descFile.exists()){
                descFile.mkdirs();
            }
            PDDocument pdf = PDDocument.load(file);
            PDFRenderer renderer = new PDFRenderer(pdf);
            int pageCount = pdf.getNumberOfPages();
            for (int i = 0; i < pageCount; i++) {
                // 方式1, 第二个参数是设置缩放比(即像素)
                // BufferedImage image = renderer.renderImageWithDPI(i, 296);
                // 方式2, 第二个参数是设置缩放比(即像素) 第二个参数越大生成图片分辨率越高，转换时间也就越长
                BufferedImage image = renderer.renderImage(i, 1.5f);
                imagePath = descPath + File.separator + i + IMAGE_SUFFIX;
                ImageIO.write(image, FORMAT_NAME, new File(imagePath));
                list.add(imagePath);
            }
            // 关闭文件, 不然该pdf文件会一直被占用
            pdf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * PDF 文件转单张图片
     *
     * @param pdfPath pdf 源文件
     * @param descPath 转换后图片存储路径
     */
    public static void pdf2Image(String pdfPath, String descPath) {
        try {
            InputStream is = new FileInputStream(pdfPath);
            PDDocument pdf = PDDocument.load(is);
            int actSize  = pdf.getNumberOfPages();
            List<BufferedImage> images = new ArrayList<>();
            for (int i = 0; i < actSize; i++) {
                BufferedImage  image = new PDFRenderer(pdf).renderImageWithDPI(i,130, ImageType.RGB);
                images.add(image);
            }
            imagesMerge(images, descPath);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 纵向处理图片, 将宽度相同的图片，竖向追加在一起. <b>注意：宽度必须相同</b>
     *
     * @param images 图片集
     * @param descPath 结果路径
     */
    private static void imagesMerge(List<BufferedImage> images, String descPath) {
        if (images == null || images.size() <= 0) {
            System.out.println("图片数组为空!");
            return;
        }
        try {
            // 总高度, 总宽度, 临时的高度或保存偏移高度, 临时的高度(主要保存每个高度), 图片的数量
            int height = 0, width = 0, _height, __height, imageCnt = images.size();
            // 保存每个文件的高度
            int[] heightArray = new int[imageCnt];
            // 保存图片流
            BufferedImage buffer;
            // 保存所有的图片的RGB
            List<int[]> imgRGB = new ArrayList<>();
            // 保存一张图片中的RGB数据
            int[] _imgRGB;
            for (int i = 0; i < imageCnt; i++) {
                buffer = images.get(i);
                // 图片高度
                heightArray[i] = _height = buffer.getHeight();
                if (i == 0) {
                    // 图片宽度
                    width = buffer.getWidth();
                }
                // 获取总高度
                height += _height;
                // 从图片中读取RGB
                _imgRGB = new int[width * _height];
                _imgRGB = buffer.getRGB(0, 0, width, _height, _imgRGB, 0, width);
                imgRGB.add(_imgRGB);
            }
            _height = 0; // 设置偏移高度为0
            // 生成新图片
            BufferedImage imageResult = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int i = 0; i < imageCnt; i++) {
                __height = heightArray[i];
                // 计算偏移高度
                if (i != 0) {
                    _height += __height;
                }
                // 写入流中
                imageResult.setRGB(0, _height, width, __height, imgRGB.get(i), 0, width);
            }
            File descFile = new File(descPath);
            // 写图片
            ImageIO.write(imageResult, FORMAT_NAME, descFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String filePath = "C:\\Users\\answer\\Desktop\\pdf\\src\\answer.pdf";
        String descPath = "C:\\Users\\answer\\Desktop\\pdf\\desc\\answer.jpg";
        pdf2Image(filePath, descPath);


        filePath = "C:\\Users\\answer\\Desktop\\pdf\\src\\answer.pdf";
        descPath = "C:\\Users\\answer\\Desktop\\pdf\\desc";
//        List<String> imageList = pdf2Images(filePath, descPath);
//        imageList.forEach(System.out::println);
    }
}