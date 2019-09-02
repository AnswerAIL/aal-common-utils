/*
 * Copyright(c) 2015-2019, Answer.AI.L
 * ShenZhen AAL Technology Co., Ltd.
 * All rights reserved.
 *
 * https://github.com/AnswerAIL/
 */
package org.answer.common.util.zip;

import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * <p>
 *     tar 包压缩解压工具类
 * </p>
 *
 * @author Answer.AI.L
 * @version 1.0
 * @date 2019-09-02
 */
public class TarUtils {
    private static final String BASE_DIR = "";
    private static final String SYMBOL_DOT = ".";
    private static final String FILE_SUFFIX = SYMBOL_DOT + ArchiveStreamFactory.TAR;


    /**
     * 压缩
     * <blockquote>
     *     srcPath = /home/answer/logs <br/>
     *     压缩文件 = /home/answer/logs.tar <br/>
     *     {@code srcPath}{@link #FILE_SUFFIX}
     * </blockquote>
     *
     * @param srcPath 待压缩文件路径
     * @throws Exception ex
     */
    public static void pack(String srcPath) throws Exception {
        pack(new File(srcPath));
    }

    /**
     * 压缩
     * <blockquote>
     *     srcFile = /home/answer/logs <br/>
     *     压缩文件 = /home/answer/logs.tar <br/>
     *     {@code srcFile}{@link #FILE_SUFFIX}
     * </blockquote>
     *
     * @param srcFile 待压缩文件路径
     * @throws Exception ex
     */
    public static void pack(File srcFile) throws Exception {
        String fileName = srcFile.getName() + FILE_SUFFIX;
        String basePath = srcFile.getParent();
        String destPath = basePath + File.separator + fileName;
        pack(srcFile, destPath);
    }

    /**
     * 压缩
     *
     * @param srcPath 待压缩文件路径
     * @param destPath 生成的压缩包的路径
     * @throws Exception ex
     */
    public static void pack(String srcPath, String destPath) throws Exception {
        File srcFile = new File(srcPath);

        pack(srcFile, destPath);
    }

    /**
     * 压缩
     *
     * @param srcFile 待压缩文件路径
     * @param destFile 生成的压缩包的路径
     * @throws Exception ex
     */
    public static void pack(File srcFile, File destFile) throws Exception {
        try (TarArchiveOutputStream taos = (TarArchiveOutputStream)
                new ArchiveStreamFactory().createArchiveOutputStream(ArchiveStreamFactory.TAR,
                        new FileOutputStream(destFile))) {
            pack(srcFile, taos, BASE_DIR);
            taos.flush();
        }
    }


    /**
     * @param srcFile 待压缩文件路径
     * @param destPath 生成的压缩包的路径
     * @throws Exception ex
     */
    public static void pack(File srcFile, String destPath) throws Exception {
        pack(srcFile, new File(destPath));
    }


    private static void pack(File srcFile, TarArchiveOutputStream taos, String basePath) throws Exception {
        if (srcFile.isDirectory()) {
            packDir(srcFile, taos, basePath);
        } else {
            packFile(srcFile, taos, basePath);
        }
    }

    private static void packDir(File dir, TarArchiveOutputStream taos, String basePath) throws Exception {
        File[] files = dir.listFiles();

        if (files == null) {
            return;
        }

        if (files.length < 1) {
            TarArchiveEntry entry = new TarArchiveEntry(basePath + dir.getName() + File.separator);

            taos.putArchiveEntry(entry);
            taos.closeArchiveEntry();
        }

        for (File file : files) {
            pack(file, taos, basePath + dir.getName() + File.separator);
        }
    }

    private static void packFile(File file, TarArchiveOutputStream taos, String dir) throws Exception {
        TarArchiveEntry entry = (TarArchiveEntry) taos.createArchiveEntry(file, dir + file.getName());
        entry.setSize(file.length());
        taos.putArchiveEntry(entry);

        IOUtils.copy(new FileInputStream(file), taos);

        taos.closeArchiveEntry();
    }



    /**
     * 解压
     * <blockquote>
     *     srcPath = /home/answer/logs.tar <br/>
     *     解压目录 = /home/answer <br/>
     *     {@code srcPath}
     * </blockquote>
     *
     * @param srcPath 待解压压缩包路径
     * @throws Exception ex
     */
    public static void unPack(String srcPath) throws Exception {
        unPack(new File(srcPath));
    }

    /**
     * 解压
     * <blockquote>
     *     srcFile = /home/answer/logs.tar <br/>
     *     解压目录 = /home/answer <br/>
     *     {@code srcFile}
     * </blockquote>
     *
     * @param srcFile 待解压压缩包路径
     * @throws Exception ex
     */
    public static void unPack(File srcFile) throws Exception {
        unPack(srcFile, srcFile.getParent());
    }

    /**
     * 解压
     *
     * @param srcFile 待解压压缩包路径
     * @param destFile 解压路径
     * @throws Exception ex
     */
    public static void unPack(File srcFile, File destFile) throws Exception {
        try(TarArchiveInputStream tais = new TarArchiveInputStream(new FileInputStream(srcFile))) {
            unPack(destFile, tais);
        }
    }

    /**
     * @param srcPath 待解压压缩包路径
     * @param destPath 解压的目录
     * @throws Exception ex
     */
    public static void unPack(String srcPath, String destPath) throws Exception {
        unPack(new File(srcPath), destPath);
    }

    /**
     * 解压
     *
     * @param srcFile 待解压压缩包路径
     * @param destPath 解压的目录
     * @throws Exception ex
     */
    public static void unPack(File srcFile, String destPath) throws Exception {
        unPack(srcFile, new File(destPath));
    }

    private static void unPack(File destFile, TarArchiveInputStream tais) throws Exception {
        TarArchiveEntry entry;

        while ((entry = tais.getNextTarEntry()) != null) {
            String dir = destFile.getPath() + File.separator + entry.getName();
            File dirFile = new File(dir);
            mkdirs(dirFile);

            // 如果解压的是对象是目录, 则新建目录
            if (entry.isDirectory()) {
                dirFile.mkdirs();
            } else {
                unPackFile(dirFile, tais);
            }

        }
    }

    private static void unPackFile(File destFile, TarArchiveInputStream tais) throws Exception {
        IOUtils.copy(tais, new FileOutputStream(destFile));
    }

    private static void mkdirs(File dirFile) {
        File parentFile = dirFile.getParentFile();

        if (!parentFile.exists()) {
            mkdirs(parentFile);
            parentFile.mkdir();
        }

    }
}