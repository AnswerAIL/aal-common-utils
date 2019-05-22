package org.answer.common.util.easyexcel.read;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

import static org.answer.common.util.Utils.toJsonFormat;

/**
 * 默认 excel 读取解析器
 *
 * @author Answer.AI.L
 * @date 2019-05-14
 */
public class AnalysisParser<T> extends AnalysisEventListener {

    /**
     * 行转换实体对象
     * */
    private Class<T> clazz;

    /** excel 数据集 */
    protected List<T> datas;

    public AnalysisParser(Class<T> clazz) {
        this.clazz = clazz;
        this.datas = new ArrayList<>();
    }

    /**
     * 解析行
     * */
    @Override
    public void invoke(Object object, AnalysisContext context) {
        T t = handler(object, context);
        this.datas.add(t);
    }

    /**
     * 清理后续工作. 如: 对象的回收工作
     * */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {}


    /**
     * 返回数据集合
     * */
    public List<T> getDatas() {
        return datas;
    }


    /**
     * 转换 excel 中的行数据为 T 对象
     * */
    protected T handler(Object object, AnalysisContext context) {
        ArrayList row = (ArrayList) object;
        Object[] array = row.toArray();

        String json = String.format(toJsonFormat(clazz), array);
        return JSON.parseObject(json, clazz);
    }
}