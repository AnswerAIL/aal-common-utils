package org.answer.common.util.easyexcel.write;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Font;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.Table;
import com.alibaba.excel.metadata.TableStyle;
import com.alibaba.excel.support.ExcelTypeEnum;
import org.apache.poi.ss.usermodel.IndexedColors;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Answer.AI.L
 * @date 2019-05-14
 *
 * <a>https://github.com/alibaba/easyexcel/blob/master/src/test/java/com/alibaba/easyexcel/test/util/DataUtil.java</a>
 */
public class WriteExcel extends ExcelWriter {
//    private OutputStream outputStream;

    public WriteExcel(OutputStream outputStream, ExcelTypeEnum typeEnum) {
        super(outputStream, typeEnum);
//        this.outputStream = outputStream;
    }


    public static void main(String[] args) throws IOException, CloneNotSupportedException {

        List<ExportInfo> exportInfos = new ArrayList<>();
        ExportInfo  exportInfo = new ExportInfo();
        exportInfo.setName("answer");
        exportInfo.setAge(12);
        exportInfo.setAddress("pt");
        exportInfo.setEmail("answer_ljm@163.com");
        exportInfos.add(exportInfo);

        exportInfo = new ExportInfo();
        exportInfo.setName("answer1");
        exportInfo.setAge(21);
        exportInfo.setAddress("sz");
        exportInfo.setEmail("answer_ljm@163.com");
        exportInfos.add(exportInfo);

        OutputStream outputStream = new FileOutputStream(new File("C:/Users/answer/Desktop/财报系统/answer.xls"));
        WriteExcel writeExcel = new WriteExcel(outputStream, ExcelTypeEnum.XLS);
        // headLineMun: 表头占几行, 好像不起作用
        Sheet sheet = new Sheet(1, 3, ExportInfo.class);
        sheet.setSheetName("answer");
        sheet.setStartRow(3);
        // 设置自适应
//        sheet.setAutoWidth(Boolean.TRUE);
        //设置列宽 设置每列的宽度
        Map<Integer, Integer> columnWidth = new HashMap<>();
        columnWidth.put(0,10000);columnWidth.put(1,10000);columnWidth.put(2,10000);columnWidth.put(3,10000);
        sheet.setColumnWidthMap(columnWidth);
        writeExcel.write(exportInfos, sheet);

        Sheet sheet1 = new Sheet(2, 3, ExportInfo.class);
        sheet1.setSheetName("aal");
        Table table1 = new Table(1);
        table1.setHead(head());
        writeExcel.write(exportInfos, sheet1, table1);

        //写sheet2  模型上打有表头的注解
        Table table2 = new Table(2);
        table2.setTableStyle(createTableStyle());
        table2.setClazz(ExportInfo.class);
        writeExcel.write(exportInfos, sheet1, table2);




        // 关闭资源
        writeExcel.finish();
        outputStream.close();

    }

    public static TableStyle createTableStyle() {
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

    public static List<List<String>> head() {
        List<List<String>> lists = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            List<String> list = new ArrayList<>();
            list.add("姓名" + i);
            list.add("年龄" + i);
            list.add("地址" + i);
            list.add("邮箱" + i);
            lists.add(list);

        }


        return lists;
    }

//    @Override
//    public void finish() {
//        super.finish();
//        try {
//            outputStream.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}


