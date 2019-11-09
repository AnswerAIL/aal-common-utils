/*
 * Copyright(c) 2015-2019, Answer.AI.L
 * ShenZhen AAL Technology Co., Ltd.
 * All rights reserved.
 *
 * https://github.com/AnswerAIL/
 */
package org.answer.common.util.excel.acm;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelDataConvertException;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *     读解析器
 * </p>
 *
 * @author Jaemon
 * @version 1.0
 * @date 2019-11-05
 */
@Slf4j
public class AesExcelListener extends AnalysisEventListener<Map<Integer, String>> {
    // 表头
    private String[] header;
    // 工作表-数据
    private List<Map<Integer, String>> datas;

    public AesExcelListener() {
        this.datas = new ArrayList<>();
    }

    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext context) {
//        log.info("解析到一条数据:{}", JSON.toJSONString(data));
        datas.add(data);
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        final int size = headMap.size();
        this.header = new String[size];
//        log.info("解析到一条头数据:{}", JSON.toJSONString(headMap));
        headMap.forEach((k, v) -> {
            if (k < size) {
                header[k] = v;
            }
        });
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
//        log.info("sheetNo={}所有数据解析完成！", context.readSheetHolder().getSheetNo());
    }

    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {
        log.error("解析失败，但是继续解析下一行, ", exception);
        if (exception instanceof ExcelDataConvertException) {
            log.error("SheetNo={}, 第{}行，解析异常",
                    context.readSheetHolder().getSheetNo(), context.readRowHolder().getRowIndex());
        } else {
            throw exception;
        }
    }

    public List<Map<Integer, String>> datas() {
        return this.datas;
    }

    public String[] header() {
        return this.header;
    }

    public void clear() {
        this.datas.clear();
    }
}