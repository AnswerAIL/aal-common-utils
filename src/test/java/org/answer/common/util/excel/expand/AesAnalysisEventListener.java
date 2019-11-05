package org.answer.common.util.excel.expand;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * </p>
 *
 * @author answer
 * @version 1.0
 * @date 2019-11-05
 */
@Slf4j
public class AesAnalysisEventListener extends AnalysisEventListener<User> {

    private List<User> datas;

    public AesAnalysisEventListener() {
        this.datas = new ArrayList<>();
    }

    @Override
    public void invoke(User data, AnalysisContext context) {
        log.info("解析到一条数据:{}", JSON.toJSONString(data));
        datas.add(data);
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        log.info("解析到一条头数据:{}", JSON.toJSONString(headMap));
//        super.invokeHeadMap(headMap, context);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("sheetNo={}所有数据解析完成！", context.readSheetHolder().getSheetNo());
//        datas.clear();
    }

    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {
        log.error("解析失败，但是继续解析下一行, ", exception);
        // 如果是某一个单元格的转换异常 能获取到具体行号
        // 如果要获取头的信息 配合invokeHeadMap使用
        if (exception instanceof ExcelDataConvertException) {
//            ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException)exception;
            log.error("SheetNo={}, 第{}行，解析异常",
                    context.readSheetHolder().getSheetNo(), context.readRowHolder().getRowIndex());
        } else {
            throw exception;
        }
    }

    public List<User> datas() {
        return this.datas;
    }

    public void clear() {
        this.datas.clear();
    }
}