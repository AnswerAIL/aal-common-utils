/*
 * Copyright(c) 2015-2019, Answer.AI.L
 * ShenZhen AAL Technology Co., Ltd.
 * All rights reserved.
 *
 * https://github.com/AnswerAIL/
 */
package com.jaemon.common.utils.easyexcel.write;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Font;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.Table;
import com.alibaba.excel.metadata.TableStyle;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.IndexedColors;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Answer.AI.L
 * @date 2019-05-14
 *
 * <a>https://github.com/alibaba/easyexcel/blob/master/src/test/java/com/alibaba/easyexcel/test/util/DataUtil.java</a>
 */
public class WriteExcel extends ExcelWriter {

    public WriteExcel(OutputStream outputStream, ExcelTypeEnum typeEnum) {
        super(outputStream, typeEnum);
    }


    public static void main(String[] args) throws IOException, CloneNotSupportedException {

        List<UserInfo> userInfos = createData();

        OutputStream outputStream = new FileOutputStream(new File("C:/Users/answer/Desktop/answer.xls"));
        WriteExcel writeExcel = new WriteExcel(outputStream, ExcelTypeEnum.XLS);

        /* sheet-1 */
        Sheet sheet1 = new Sheet(1, 1, UserInfo.class);
        sheet1.setSheetName("sheet-1");
        // 数据行从第几行开始
        sheet1.setStartRow(0);
        // 设置自适应
//        sheet.setAutoWidth(Boolean.TRUE);
        //设置列宽 设置每列的宽度
        Map<Integer, Integer> columnWidth = ImmutableMap.of(
                0,10000,
                1,10000,
                2,10000,
                3,10000
        );
        sheet1.setColumnWidthMap(columnWidth);
        writeExcel.write(userInfos, sheet1);

        /* sheet-2 */
        Sheet sheet2 = new Sheet(2);
        sheet2.setSheetName("sheet-2");


        /* sheet-2 table1 使用自定义头部 */
        Table table1 = new Table(1);
        table1.setHead(head());
        table1.setClazz(UserInfo.class);
        writeExcel.write(userInfos, sheet2, table1);

        /* sheet-2 table2 使用注释头部 */
        Table table2 = new Table(2);
        table2.setTableStyle(createTableStyle());
        table2.setClazz(UserInfo.class);
        writeExcel.write(userInfos, sheet2, table2);

        // 关闭资源
        writeExcel.finish();
        outputStream.close();

    }


    private static List<UserInfo> createData() {
        List<UserInfo> userInfos = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            userInfos.add(
                    new UserInfo(
                    "answer-" + i,
                            12 + i,
                            "pt",
                            "answer_ljm@163.com"));
        }

        return userInfos;
    }


    private static TableStyle createTableStyle() {
        TableStyle tableStyle = new TableStyle();
        Font headFont = new Font();
        headFont.setBold(true);
        headFont.setFontHeightInPoints((short)22);
        headFont.setFontName("楷体");
        // 表头字体样式
        tableStyle.setTableHeadFont(headFont);
        // 表头背景颜色
        tableStyle.setTableHeadBackGroundColor(IndexedColors.BLUE);

        Font contentFont = new Font();
        contentFont.setBold(true);
        contentFont.setFontHeightInPoints((short)22);
        contentFont.setFontName("黑体");
        // 表格内容字体样式
        tableStyle.setTableContentFont(contentFont);
        // 表格内容背景颜色
        tableStyle.setTableContentBackGroundColor(IndexedColors.GREEN);
        return tableStyle;
    }

    private static List<List<String>> head() {
        List<List<String>> lists = new ArrayList<>();

        lists.add(Lists.newArrayList("姓名"));
        lists.add(Lists.newArrayList("年龄"));
        lists.add(Lists.newArrayList("邮箱"));
        lists.add(Lists.newArrayList("地址"));

        return lists;
    }

}


