package org.answer.common.util.zip;

import com.google.common.collect.Lists;

import java.io.File;
import java.util.List;

/**
 * <p>
 *     zip4j 压缩解压公工具类测试
 * </p>
 *
 * @author Answer.AI.L
 * @version 1.0
 * @date 2019-08-30
 */
public class ZipUtilsApp {

    private static final String PREV_PATH = "C:\\Users\\answer\\Desktop\\zip\\";

    public static void main(String[] args) throws Exception {
        unzip();

    }


    /**
     * 逐个解压, 可以进行过滤处理
     * */
    private static void unzip() throws Exception {
        String zipPath = PREV_PATH + "package\\pkg.zip";
        String descPath = PREV_PATH + "desc";
        ZipUtils.unzip(zipPath, descPath, "123456");
    }

    /**
     * 解压
     * */
    private static void unzipEn() throws Exception {
        String zipPath = PREV_PATH + "package\\pkg.zip";
        String descPath = PREV_PATH + "desc";
        ZipUtils.unzipEn(zipPath, descPath);
    }


    /**
     * 将文件以文件流的形式添加到已有压缩包中, 可设置文件在压缩包中的文件名
     * */
    private static void zipStream() throws Exception {
        String zipPath = PREV_PATH + "package\\pkg.zip";
        ZipUtils.zipStream(zipPath, PREV_PATH + "answer.xlsx", "AnswerAIL.xlsx");
    }


    /**
     * 如果文件比较大, 设置分卷压缩
     * */
    private static void zipSplit() throws Exception {
        String zipPath = PREV_PATH + "package\\pkg.zip";
        List<File> files = Lists.newArrayList(
                new File(PREV_PATH + "answer.xlsx"),
                new File(PREV_PATH + "cash.xls"),
                new File(PREV_PATH + "balance.xls"),
                new File(PREV_PATH + "用户数据表.xlsx")
        );
        // 按 1M 进行分卷
        ZipUtils.zipSplit(zipPath, files, 1024 * 1024 * 10);
    }

    /**
     * 压缩加密码保护
     * */
    private static void zipPwd() throws Exception {
        String zipPath = PREV_PATH + "package\\pkg.zip";
        List<File> files = Lists.newArrayList(
                new File(PREV_PATH + "answer.xlsx"),
                new File(PREV_PATH + "cash.xls"),
                new File(PREV_PATH + "balance.xls"),
                new File(PREV_PATH + "用户数据表.xlsx")
        );
        ZipUtils.zip(zipPath, files, "123456");
    }


    /**
     * 压缩
     * */
    private static void zip() throws Exception {
        String zipPath = PREV_PATH + "package\\pkg.zip";
        List<File> files = Lists.newArrayList(
                new File(PREV_PATH + "answer.xlsx"),
                new File(PREV_PATH + "cash.xls"),
                new File(PREV_PATH + "balance.xls"),
                new File(PREV_PATH + "用户数据表.xlsx")
        );
        ZipUtils.zip(zipPath, files);
    }

}