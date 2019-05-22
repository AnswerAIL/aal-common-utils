package org.answer.common.util.easyexcel;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.support.ExcelTypeEnum;
import org.answer.common.util.easyexcel.read.AnalysisParser;

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
     * 读取 ***.xls 格式excel
     * @param excelFile excel 文件
     * @param analysisParser 解析器
     *
     * @return 'List<List<String>>'
     * */
    public static <T> List<T> readXlsExcel(File excelFile, AnalysisParser<T> analysisParser) {
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
    public static <T> List<T> readXlsxExcel(File excelFile, AnalysisParser<T> analysisParser) {
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
    public static <T> List<T> readExcel(File excelFile, ExcelTypeEnum excelTypeEnum, AnalysisParser<T> analysisParser) {
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