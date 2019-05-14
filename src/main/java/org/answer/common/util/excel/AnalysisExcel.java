package org.answer.common.util.excel;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.support.ExcelTypeEnum;

import java.io.*;

import static org.answer.common.util.Constants.COMMA_SEP;
import static org.answer.common.util.TransUtils.transLetter2Num;

/**
 * @author Answer.AI.L
 * @date 2019-05-14
 */
public class AnalysisExcel {


    /**
     * @param inputStream .
     * @param analysisEventListener .
     * */
    public static void readXlsExcel(InputStream inputStream, AnalysisEventListener analysisEventListener) {
        readExcel(inputStream, ExcelTypeEnum.XLS, analysisEventListener);
    }


    /**
     * @param inputStream .
     * @param analysisEventListener .
     * */
    public static void readXlsxExcel(InputStream inputStream, AnalysisEventListener analysisEventListener) {
        readExcel(inputStream, ExcelTypeEnum.XLSX, analysisEventListener);
    }


    /**
     * @param inputStream .
     * @param excelTypeEnum .
     * @param analysisEventListener .
     * */
    public static void readExcel(InputStream inputStream, ExcelTypeEnum excelTypeEnum, AnalysisEventListener analysisEventListener) {
        try {
            ExcelReader excelReader = new ExcelReader(inputStream, excelTypeEnum, null, analysisEventListener);
            excelReader.read();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 将所有的字母转换为对应的数字
     *
     * @param cols eg: A,BA,D -> 0,52,3
     * */
    public static String transAllLetter2Num(String cols) {
        StringBuffer sb = new StringBuffer();
        String[] letterIdxs = cols.split(COMMA_SEP);
        for (String letterIdx: letterIdxs) {
            long rlt = transLetter2Num(letterIdx);
            sb.append(rlt).append(COMMA_SEP);
        }
        return sb.deleteCharAt(sb.length() - 1).toString();

    }


    public static void main(String[] args) throws FileNotFoundException {
        InputStream inputStream = new FileInputStream(new File("src/main/resources/aal_balance.xls"));
        BalanceAnalysis balanceAnalysis = new BalanceAnalysis(transAllLetter2Num("A,D"), transAllLetter2Num("B,E"), transAllLetter2Num("C,F"));

        AnalysisExcel.readXlsExcel(inputStream, balanceAnalysis);

        System.out.println(balanceAnalysis.getDatas().keySet());
    }

}