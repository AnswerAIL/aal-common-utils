package org.answer.common.util.easyexcel;

import com.alibaba.excel.context.AnalysisContext;

import java.util.List;

/**
 * 自定义 excel 读取解析器
 *
 * @author Answer.AI.L
 * @date 2019-05-14
 */
public class CustomParser extends AnalysisParser<AnalysisParser> {

    @Override
    @SuppressWarnings("unchecked")
    public void invoke(Object object, AnalysisContext context) {
        // 过滤掉首行
        if (context.getCurrentRowNum() > 0) {
            datas.add((List<String>) object);
        }
    }

}