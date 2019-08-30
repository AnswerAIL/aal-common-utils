/*
 * Copyright(c) 2015-2019, Answer.AI.L
 * ShenZhen AAL Technology Co., Ltd.
 * All rights reserved.
 *
 * https://github.com/AnswerAIL/
 */
package org.answer.common.util.zip;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.CompressionLevel;
import net.lingala.zip4j.model.enums.CompressionMethod;
import net.lingala.zip4j.model.enums.EncryptionMethod;

import java.io.*;
import java.util.List;

/**
 * <p>
 *     zip 包压缩解压工具类
 * </p>
 *
 * @author Answer.AI.L
 * @version 1.0
 * @date 2019-08-28
 */
//@Slf4j
public class ZipUtils {

    private ZipUtils() {}

    /**
     * 压缩
     *
     * @param zipFilePath 压缩包文件路径
     * @param zipFiles 待压缩文件列表
     * @throws ZipException ze
     */
    public static void zip(String zipFilePath, List<File> zipFiles) throws ZipException {
        zip(zipFilePath, zipFiles, null);
    }

    /**
     * 压缩
     *
     * @param zipFilePath 压缩包文件路径
     * @param zipFiles 待压缩文件列表
     * @param pwd 设置压缩包密码(注意待压缩文件列表中包含文件时才有效, 如果只是包含目录则密码设置无效)
     * @throws ZipException ze
     */
    public static void zip(String zipFilePath, List<File> zipFiles, String pwd) throws ZipException {
        zipFiles.removeIf(file -> {
            boolean notExists = !file.exists();
//            if (notExists) {
//                log.warn("file=[{}] is not exists", file.getPath());
//            }
            return notExists;
        });

        if (zipFiles.size() < 1) {
//            log.warn("file size is zero");
            return;
        }

        // 如果压缩包目录不存在则新建
        String zipParentPath = new File(zipFilePath).getParent();
        if (!new File(zipParentPath).exists()) {
            new File(zipParentPath).mkdirs();
        }

        ZipFile zipFile;
        ZipParameters parameters = new ZipParameters();
        parameters.setCompressionMethod(CompressionMethod.DEFLATE);
        parameters.setCompressionLevel(CompressionLevel.NORMAL);

        if (isEmpty(pwd)) {
            zipFile = new ZipFile(zipFilePath);
        } else {
            zipFile = new ZipFile(zipFilePath, pwd.toCharArray());
            // set password
            parameters.setEncryptFiles(true);
            parameters.setEncryptionMethod(EncryptionMethod.ZIP_STANDARD);
//            parameters.setEncryptionMethod(EncryptionMethod.AES);
//            parameters.setAesKeyStrength(AesKeyStrength.KEY_STRENGTH_256);
        }

        zipFile.addFiles(zipFiles, parameters);

//        log.info("zipFile is Encrypted={}.", zipFile.isEncrypted());
    }



    /**
     * 分卷压缩
     *
     * @param zipFilePath 压缩包文件路径
     * @param zipFiles 待压缩文件列表
     * @throws ZipException ze
     */
    public static void zipSplit(String zipFilePath, List<File> zipFiles) throws ZipException {
        zipSplit(zipFilePath, zipFiles, 65535, null);
    }


    /**
     * 分卷压缩
     *
     * @param zipFilePath 压缩包文件路径
     * @param zipFiles 待压缩文件列表
     * @param pwd 设置压缩密码
     * @throws ZipException ze
     */
    public static void zipSplit(String zipFilePath, List<File> zipFiles, String pwd) throws ZipException {
        zipSplit(zipFilePath, zipFiles, 65535, pwd);
    }

    /**
     * 分卷压缩
     *
     * @param zipFilePath 压缩包文件路径
     * @param zipFiles 待压缩文件列表
     * @param splitLength 分卷大小(<b>splitLength minimum: 65536</b>)
     * @throws ZipException ze
     * */
    public static void zipSplit(String zipFilePath, List<File> zipFiles, long splitLength) throws ZipException {
        zipSplit(zipFilePath, zipFiles, splitLength, null);
    }

    /**
     * 分卷压缩
     *
     * @param zipFilePath 压缩包文件路径
     * @param zipFiles 待压缩文件列表
     * @param splitLength 分卷大小(<b>splitLength minimum: 65536</b>)
     * @param pwd 设置压缩密码
     * @throws ZipException ze
     * */
    public static void zipSplit(String zipFilePath, List<File> zipFiles, long splitLength, String pwd) throws ZipException {
        ZipFile zipFile;

        ZipParameters parameters = new ZipParameters();
        parameters.setCompressionMethod(CompressionMethod.DEFLATE);
        parameters.setCompressionLevel(CompressionLevel.NORMAL);

        if (isEmpty(pwd)) {
            zipFile = new ZipFile(zipFilePath);
        } else {
            zipFile = new ZipFile(zipFilePath, pwd.toCharArray());

            // set password
            parameters.setEncryptFiles(true);
            parameters.setEncryptionMethod(EncryptionMethod.ZIP_STANDARD);
        }

        // splitLength minimum: 65536
        splitLength = splitLength < 65536 ? 65536 : splitLength;
        zipFile.createSplitZipFile(zipFiles, parameters, true, splitLength);
    }



    /**
     * 压缩(将文件以流的形式添加到压缩包)
     *
     * @param zipFilePath 压缩包文件路径
     * @param filePath 待压缩的文件路径
     * @throws IOException ie
     */
    public static void zipStream(String zipFilePath, String filePath) throws IOException {
        File file = new File(filePath);
        zipStream(zipFilePath, file, file.getName(), null);
    }

    /**
     * 压缩(将文件以流的形式添加到压缩包)
     *
     * @param zipFilePath 压缩包文件路径
     * @param filePath 待压缩的文件路径
     * @param pwd 设置压缩密码
     * @throws IOException ie
     */
    public static void zipStreamPwd(String zipFilePath, String filePath, String pwd) throws IOException {
        File file = new File(filePath);
        zipStream(zipFilePath, file, file.getName(), pwd);
    }

    /**
     * 压缩(将文件以流的形式添加到压缩包)
     *
     * @param zipFilePath 压缩包文件路径
     * @param filePath 待压缩的文件路径
     * @param fileNameInZip 文件在压缩包内的文件名
     * @throws IOException ie
     */
    public static void zipStream(String zipFilePath, String filePath, String fileNameInZip) throws IOException {
        zipStream(zipFilePath, new File(filePath), fileNameInZip, null);
    }

    /**
     * 压缩(将文件以流的形式添加到压缩包)
     *
     * @param zipFilePath 压缩包文件路径
     * @param filePath 待压缩的文件路径
     * @param fileNameInZip 文件在压缩包内的文件名
     * @param pwd 设置压缩密码
     * @throws IOException ie
     */
    public static void zipStream(String zipFilePath, String filePath, String fileNameInZip, String pwd) throws IOException {
        zipStream(zipFilePath, new File(filePath), fileNameInZip, pwd);
    }

    /**
     * 压缩(将文件以流的形式添加到压缩包)
     *
     * @param zipFilePath 压缩包文件路径
     * @param file 待压缩的文件
     * @param fileNameInZip 文件在压缩包内的文件名
     * @throws IOException ie
     */
    public static void zipStream(String zipFilePath, File file, String fileNameInZip) throws IOException {
        zipStream(zipFilePath, file, fileNameInZip, null);
    }

    /**
     * 压缩(将文件以流的形式添加到压缩包)
     *
     * @param zipFilePath 压缩包文件路径
     * @param file 待压缩的文件
     * @param fileNameInZip 文件在压缩包内的文件名
     * @param pwd 设置压缩密码
     * @throws IOException ie
     */
    public static void zipStream(String zipFilePath, File file, String fileNameInZip, String pwd) throws IOException {
        if (!file.exists()) {
//            log.info("file[{}] is not exists", file.getPath());
            return;
        }

        try (InputStream is = new FileInputStream(file)) {
            ZipFile zipFile;
            ZipParameters parameters = new ZipParameters();
            parameters.setCompressionMethod(CompressionMethod.DEFLATE);

            if (isEmpty(pwd)) {
                zipFile = new ZipFile(zipFilePath);
            } else {
                zipFile = new ZipFile(zipFilePath, pwd.toCharArray());

                // set password
                parameters.setEncryptFiles(true);
                parameters.setEncryptionMethod(EncryptionMethod.ZIP_STANDARD);
            }

            fileNameInZip = isNotEmpty(fileNameInZip) ? fileNameInZip : file.getName();
            parameters.setFileNameInZip(fileNameInZip);

            zipFile.addStream(is, parameters);
        }
    }



    /**
     * 解压(不支持文件名称为中文)
     *
     * @param srcPath 待解压的 zip 文件路径
     * @param descPath 解压路径
     * @throws ZipException ze
     */
    public static void unzipEn(String srcPath, String descPath) throws ZipException {
        unzipEn(new File(srcPath), descPath, null);
    }

    /**
     * 解压(不支持文件名称为中文)
     *
     * @param srcPath 待解压的 zip 文件路径
     * @param descPath 解压路径
     * @param pwd 设置解压密码
     * @throws ZipException ze
     */
    public static void unzipEn(String srcPath, String descPath, String pwd) throws ZipException {
        unzipEn(new File(srcPath), descPath, pwd);
    }

    /**
     * 解压(不支持文件名称为中文)
     *
     * @param srcFile 待解压的 zip 文件
     * @param descPath 解压路径
     * @throws ZipException ze
     */
    public static void unzipEn(File srcFile, String descPath) throws ZipException {
        unzipEn(srcFile, descPath, null);
    }

    /**
     * 解压(不支持文件名称为中文)
     *
     * @param srcFile 待解压的 zip 文件
     * @param descPath 解压路径
     * @param pwd 设置解压密码
     * @throws ZipException ze
     */
    public static void unzipEn(File srcFile, String descPath, String pwd) throws ZipException {
        String srcPath = srcFile.getPath();
        if (!srcFile.exists()) {
//            log.info("file=[{}] is not exists", srcPath);
            return;
        }

        ZipFile zipFile = new ZipFile(srcFile);

        if (!zipFile.isValidZipFile()) {
//            log.info("file=[{}] is not valid zip file", srcPath);
            return;
        }

        if (zipFile.isEncrypted()) {
            zipFile = new ZipFile(srcFile, pwd.toCharArray());
        }

        zipFile.extractAll(descPath);
    }



    /**
     * 解压
     *
     * @param srcPath 待解压的 zip 文件路径
     * @param descPath 解压路径
     * @throws ZipException ze
     */
    public static void unzip(String srcPath, String descPath) throws ZipException {
        File file = new File(srcPath);
        unzip(file, descPath, null);
    }

    /**
     * 解压
     *
     * @param srcFile 待解压的 zip 文件路径
     * @param descPath 解压路径
     * @throws ZipException ze
     */
    public static void unzip(File srcFile, String descPath) throws ZipException {
        unzip(srcFile, descPath, null);
    }

    /**
     * 解压
     *
     * @param srcPath 待解压的 zip 文件路径路径
     * @param descPath 解压路径
     * @param password zip 包是否设置密码
     * @throws ZipException ze
     */
    public static void unzip(String srcPath, String descPath, String password) throws ZipException {
        File file = new File(srcPath);
        unzip(file, descPath, password);
    }


    /**
     * 解压
     *
     * @param srcFile 待解压的 zip 文件路径
     * @param descPath 解压路径
     * @param password zip 包是否设置密码
     * @throws ZipException ze
     */
    public static void unzip(File srcFile, String descPath, String password) throws ZipException {
        String srcPath = srcFile.getPath();
        if (!srcFile.exists()) {
//            log.info("file=[{}] is not exists", srcPath);
            return;
        }

        ZipFile zipFile = new ZipFile(srcPath);

        if (!zipFile.isValidZipFile()) {
//            log.info("file=[{}] is not valid zip file", srcPath);
            return;
        }

        if (zipFile.isEncrypted()) {
            zipFile = new ZipFile(srcFile, password.toCharArray());
        }

        List<FileHeader> fileHeaders = zipFile.getFileHeaders();
        for (FileHeader fileHeader : fileHeaders) {
            try {
                // 解决中文文件名乱码问题
                String fileName = new String(fileHeader.getFileName().getBytes("CP437"), "gbk");
//                log.info("fileName=[{}]", fileName);
                zipFile.extractFile(fileHeader, descPath, fileName);
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }
        }
    }


    private static ZipFile zipFile(File file, String pwd) {
        ZipFile zipFile;
        if (isEmpty(pwd)) {
            zipFile = new ZipFile(file);
        } else {
            zipFile = new ZipFile(file, pwd.toCharArray());
        }
        return zipFile;
    }

    private static boolean isNotEmpty(Object str) {
        return !isEmpty(str);
    }

    private static boolean isEmpty(Object str) {
        return (str == null || "".equals(str));
    }


    public static void main(String[] args) throws Exception {
        zipStream("C:\\Users\\answer\\Desktop\\zip\\ai.zip", "C:\\Users\\answer\\Desktop\\zip\\report\\balance\\123.xlsx");

        /*List<File> files = Lists.newArrayList(new File("C:\\Users\\answer\\Desktop\\zip\\report"),
                new File("C:\\Users\\answer\\Desktop\\zip\\report\\balance\\123.xlsx"),
                new File("C:\\Users\\answer\\Desktop\\zip\\report\\balance\\456.xlsx"));
        zipSplit("C:\\Users\\answer\\Desktop\\zip\\ai.zip", files);*/

//        List<File> files = Lists.newArrayList(new File("C:\\Users\\answer\\Desktop\\zip\\report"), new File("C:\\Users\\answer\\Desktop\\zip\\report\\balance\\123.xlsx"));
//        zip("C:\\Users\\answer\\Desktop\\zip\\ai.zip", files);

        /*String srcPath = "C:\\Users\\answer\\Desktop\\zip\\123.zip";
        String descPath = "C:\\Users\\answer\\Desktop\\zip";
        unzip(srcPath, descPath);*/
    }
}