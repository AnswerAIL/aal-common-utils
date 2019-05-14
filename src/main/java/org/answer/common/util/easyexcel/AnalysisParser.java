package org.answer.common.util.easyexcel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 默认 excel 读取解析器
 *
 * @author Answer.AI.L
 * @date 2019-05-14
 */
public class AnalysisParser<T extends AnalysisParser> extends AnalysisEventListener {

    /** excel 数据集 */
    protected List<List<String>> datas;

    public AnalysisParser() {
        this.datas = new ArrayList<>();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void invoke(Object object, AnalysisContext context) {
        List<String> row = (List<String>) object;
        datas.add(row);
    }

    /**
     * 清理后续工作. 如: 对象的回收工作
     * */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }


    protected List<List<String>> getDatas() {
        return datas;
    }
}