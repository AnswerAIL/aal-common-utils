package org.answer.common.util.easyexcel;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.support.ExcelTypeEnum;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author Answer.AI.L
 * @date 2019-05-14
 */
public class ExcelUtils {

    /**
     * 读取 ***.xls 格式excel, 默认使用 {@link AnalysisParser} 解析器
     * @param excelFile excel 文件
     *
     * @return 'List<List<String>>'
     * */
    public static List<List<String>> readXlsExcel(File excelFile) {
        return readExcel(excelFile, ExcelTypeEnum.XLS, new AnalysisParser<>());
    }

    /**
     * 读取 ***.xlsx 格式excel, 默认使用 {@link AnalysisParser} 解析器
     *
     * @param excelFile excel 文件
     *
     * @return 'List<List<String>>'
     * */
    public static List<List<String>> readXlsxExcel(File excelFile) {
        return readExcel(excelFile, ExcelTypeEnum.XLSX, new AnalysisParser<>());
    }


    /**
     * 读取 ***.xls 格式excel
     * @param excelFile excel 文件
     * @param analysisParser 解析器
     *
     * @return 'List<List<String>>'
     * */
    public static List<List<String>> readXlsExcel(File excelFile, AnalysisParser<? extends AnalysisParser> analysisParser) {
        return readExcel(excelFile, ExcelTypeEnum.XLS, analysisParser);
    }


    /**
     * 读取 ***.xlsx 格式excel
     *
     * @param excelFile excel 文件
     * @param analysisParser 解析器
     *
     * @return 'List<List<String>>'
     * */
    public static List<List<String>> readXlsxExcel(File excelFile, AnalysisParser<? extends AnalysisParser> analysisParser) {
        return readExcel(excelFile, ExcelTypeEnum.XLSX, analysisParser);
    }


    /**
     * 读取指定格式 excel
     *
     * @param excelFile excel 文件
     * @param excelTypeEnum excel 类型 {@link ExcelTypeEnum}
     * @param analysisParser 解析器
     *
     * @return 'List<List<String>>'
     * */
    public static List<List<String>> readExcel(File excelFile, ExcelTypeEnum excelTypeEnum, AnalysisParser<? extends AnalysisParser> analysisParser) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(excelFile);
            ExcelReader excelReader = new ExcelReader(inputStream, excelTypeEnum, null, analysisParser);
            excelReader.read();
            return analysisParser.getDatas();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}