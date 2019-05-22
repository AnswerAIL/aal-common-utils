package org.answer.common.util.excel;

import com.alibaba.fastjson.JSON;
import org.answer.common.util.easyexcel.read.AnalysisParser;
import org.answer.common.util.easyexcel.read.ExcelUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;


/**
 * @author Answer.AI.L
 * @date 2019-05-14
 */
public class EasyExcelApp {

    public static void main(String[] args) throws FileNotFoundException {
        List<User> datas = ExcelUtils.readXlsExcel(new File("src/test/resources/users.xls"), new UserAnalysisParser(User.class));

        for (User data: datas) {
            System.out.println(JSON.toJSONString(data));
        }

        System.out.println();
        System.out.println("##################################################");
        System.out.println();


        datas = ExcelUtils.readXlsExcel(new File("src/test/resources/users.xls"), new AnalysisParser<>(User.class));

        for (User data: datas) {
            System.out.println(JSON.toJSONString(data));
        }
    }

}