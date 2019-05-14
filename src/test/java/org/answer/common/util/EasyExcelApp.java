package org.answer.common.util;

import org.answer.common.util.easyexcel.CustomParser;
import org.answer.common.util.easyexcel.ExcelUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;


/**
 * @author Answer.AI.L
 * @date 2019-05-14
 */
public class EasyExcelApp {

    public static void main(String[] args) throws FileNotFoundException {
//        List<List<String>> datas = ExcelUtils.readXlsExcel(new File("src/test/resources/easyexcel.xls"));

        List<List<String>> datas = ExcelUtils.readXlsExcel(new File("src/test/resources/easyexcel.xls"), new CustomParser());

        for (List data: datas) {
            System.out.println(data);
        }
    }

}