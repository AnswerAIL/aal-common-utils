package org.answer.common.util.excel.expand;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author answer
 * @version 1.0
 * @date 2019-11-05
 */
@Slf4j
public class ExcelApp {

    public static void main(String[] args) {
        writeAll();

//        read();

//        readAll();

//        readSync();
    }


    private static void readSync() {
        String fileName = "src/test/resources/sys_user.xlsx";
        // 0 为不存在行头, 默认为1, 多行头设置其他值
        List<Object> list = EasyExcel.read(fileName).head(User.class).sheet().headRowNumber(0).doReadSync();
        log.info("list={}", JSON.toJSONString(list));
        System.out.println();

        list = EasyExcel.read(fileName).head(User.class).sheet().headRowNumber(1).doReadSync();
        log.info("list={}", JSON.toJSONString(list));
    }

    private static void readAll() {
        String fileName = "src/test/resources/sys_user.xlsx";
        AesAnalysisEventListener aesAnalysisEventListener = new AesAnalysisEventListener();
        // 0 为不存在行头, 默认为1, 多行头设置其他值
        ExcelReader excelReader = EasyExcel.read(fileName, User.class, aesAnalysisEventListener).headRowNumber(0).build();
        // 同步返回
//        List<Object> list = EasyExcel.read(fileName).head(User.class).sheet().doReadSync();

        for (ReadSheet readSheet : excelReader.excelExecutor().sheetList()) {
            excelReader.read(readSheet);

            List<User> datas = aesAnalysisEventListener.datas();
            log.info("sheetName={}, 结果返回={}", readSheet.getSheetName(), JSON.toJSONString(datas));
        }

        // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
        excelReader.finish();
    }


    public static void read() {
        String fileName = "src/test/resources/sys_user.xlsx";
        AesAnalysisEventListener aesAnalysisEventListener = new AesAnalysisEventListener();
        // headRowNumber=0 没有表头, 默认为1, 即第一行为表头
        ExcelReader excelReader = EasyExcel.read(fileName, User.class, aesAnalysisEventListener).headRowNumber(0).build();
        // 全局转换器设置, 所有java为string,excel为string的都会用 MyStringConverter 这个转换器
//        EasyExcel.read(fileName, User.class, aesAnalysisEventListener).registerConverter(new MyStringConverter()).build();

        // 获取 sheet 列表
        for (ReadSheet readSheet : excelReader.excelExecutor().sheetList()) {
            log.info("sheetName={}", readSheet.getSheetName());
        }

        ReadSheet readSheet1 = EasyExcel.readSheet(0).build();
        excelReader.read(readSheet1);
        List<User> datas = aesAnalysisEventListener.datas();
        log.info("SheetName={}, 结果返回={}", readSheet1.getSheetNo(), JSON.toJSONString(datas));
        aesAnalysisEventListener.clear();
        System.out.println();


        ReadSheet readSheet2 = EasyExcel.readSheet(1).build();
        excelReader.read(readSheet2);
        datas = aesAnalysisEventListener.datas();
        log.info("SheetNo={}, 结果返回={}", readSheet2.getSheetNo(), JSON.toJSONString(datas));
        aesAnalysisEventListener.clear();
        System.out.println();
        System.out.println();

        // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
        excelReader.finish();





        // 方法2 如果 sheet1 sheet2 数据不一致的话
        fileName = "src/test/resources/sys_user.xlsx";
        aesAnalysisEventListener = new AesAnalysisEventListener();
        excelReader = EasyExcel.read(fileName).build();
        // 这里为了简单 所以注册了 同样的head 和Listener 自己使用功能必须不同的Listener
        readSheet1 = EasyExcel.readSheet(0).head(User.class).registerReadListener(aesAnalysisEventListener).build();
        excelReader.read(readSheet1);
        datas = aesAnalysisEventListener.datas();
        log.info("SheetNo={}, 结果返回={}", readSheet1.getSheetNo(), JSON.toJSONString(datas));
        aesAnalysisEventListener.clear();
        System.out.println();


        readSheet2 = EasyExcel.readSheet(1).head(User.class).registerReadListener(aesAnalysisEventListener).build();
        excelReader.read(readSheet2);
        datas = aesAnalysisEventListener.datas();
        log.info("SheetNo={}, 结果返回={}", readSheet2.getSheetNo(), JSON.toJSONString(datas));
        aesAnalysisEventListener.clear();

        // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
        excelReader.finish();
    }

    private static void writeAll() {
        String fileName = "src/test/resources/asset_data.xlsx";
        ExcelWriter excelWriter = EasyExcel.write(fileName, AssetData.class).build();
//        WriteSheet writeSheet = EasyExcel.writerSheet("模板").build();
        WriteSheet writeSheet;
        for (int i = 0; i < 5; i++) {
            writeSheet = EasyExcel.writerSheet(i, "资产负债-" + i).build();
            excelWriter.write(data(i), writeSheet);
        }
        // 千万别忘记finish 会帮忙关闭流
        excelWriter.finish();
    }

    private static void write() {
        String fileName = "src/test/resources/asset_data.xlsx";
        ExcelWriterBuilder write = EasyExcel.write(fileName, AssetData.class);
        write.sheet("资产负债").doWrite(data(0));
    }

    private static List<AssetData> data(int no) {
        List<AssetData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            AssetData data = new AssetData();
            data.setUserName("no" + no + "Answer-" + i);
            data.setPassword("123456");
            data.setStatus((byte) i);
            data.setDate(new Date());
            data.setAssetAmt(new Random().nextDouble() * 100);
            list.add(data);
        }
        return list;
    }


    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
    /**
     * 导出 Excel 功能
     */
    /*public void exportExcel(HttpServletResponse response) throws Exception {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("UTF-8");
        String sheetName = "导出列表";

        String fileName = sheetName + UNDERLINE + LocalDateTime.now().format(DATETIME_FORMATTER);
        // 火狐浏览器要区别设置， 转成 ISO-8859-1 编码
        if (request.getHeader("user-agent").toLowerCase().contains("firefox")) {
            fileName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        } else {
            fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.name());
        }
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

        EasyExcel.write(response.getOutputStream(), AssetData.class).sheet(0, "资产数据").doWrite(Lists.newArrayList(data(0)));
    }*/


    // 使用 request.getParameter("XXX"); 获取过滤参数值
    /*public static void exportExcel(HttpServletRequest request, HttpServletResponse response, String sheetName, Class head, List data)
            throws Exception {
        String userAgent = "user-agent", ff = "firefox";
        String fileName = sheetName + UNDERLINE + LocalDateTime.now().format(DATETIME_FORMATTER);
        if (request.getHeader(userAgent).toLowerCase().contains(ff)) {
            fileName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        } else {
            fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.name());
        }
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), head).sheet(0, sheetName).doWrite(data);
    }*/
}