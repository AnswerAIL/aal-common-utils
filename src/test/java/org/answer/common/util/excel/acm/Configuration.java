/*
 * Copyright(c) 2015-2019, Answer.AI.L
 * ShenZhen AAL Technology Co., Ltd.
 * All rights reserved.
 *
 * https://github.com/AnswerAIL/
 */
package org.answer.common.util.excel.acm;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

/**
 * <p>
 *     配置类
 * </p>
 *
 * @author Jaemon
 * @version 1.0
 * @date 2019-11-09
 */
@Data
@AllArgsConstructor
public class Configuration {
    // key 值
    private String key;
    // excel 中表头中文值
    private String cnKey;
    // 数据类型 DataTypeEnum
    private String dataType;
    // 默认值
    private String defaultKey;
    // 展示值
    private String showName;
    // 字段值
    private String dictName;

    private String array;
    private int index;

    public Configuration(String key, String cnKey, String dataType, String defaultKey, String showName, String dictName) {
        this.key = key;
        this.cnKey = cnKey;
        this.dataType = dataType;
        this.defaultKey = defaultKey;
        this.showName = showName;
        this.dictName = dictName;
    }

    public static Map<String, Configuration> configs() {
        Map<String, Configuration> configurations = Maps.newHashMap();

        // 先转字典, 再转类型
        configurations.put("资产包名称", new Configuration("main001", "资产包名称", "string", "", "", ""));
        configurations.put("借款合同编号", new Configuration("main002", "借款合同编号", "string", "", "", ""));
        configurations.put("借款人编号", new Configuration("main003", "借款人编号", "string", "", "", ""));
        configurations.put("押品编号", new Configuration("main004", "押品编号", "string", "", "", ""));
        configurations.put("押品分类", new Configuration("main005", "押品分类", "string", "", "", "assetType"));
        configurations.put("经营状况", new Configuration("main006", "经营状况", "string", "", "", "status"));
        configurations.put("借款人名称", new Configuration("main007", "借款人名称", "string", "", "", ""));
        configurations.put("备注", new Configuration("main008", "备注", "string", "", "", ""));
        configurations.put("是否破产", new Configuration("main009", "是否破产", "int", "-1", "", "goBroke"));

        configurations.put("朝向", new Configuration("sub1001", "朝向", "string", "", "", ""));
        configurations.put("案例1-交易日期", new Configuration("sub1002", "案例1-交易日期", "date", "yyyy-MM-dd HH:mm:ss", "", "", "sub1Arr", 0));
        configurations.put("案例1-交易价格", new Configuration("sub1003", "案例1-交易价格", "decimal2", "-1", "", "", "sub1Arr", 0));
        configurations.put("案例2-交易日期", new Configuration("sub1004", "案例2-交易日期", "date", "yyyy-MM-dd HH:mm:ss", "", "", "sub1Arr", 1));
        configurations.put("案例2-交易价格", new Configuration("sub1005", "案例2-交易价格", "decimal2", "-1", "", "", "sub1Arr", 1));

        configurations.put("物业类型", new Configuration("sub2001", "物业类型", "string", "", "", ""));
        configurations.put("案例1-商业繁荣度", new Configuration("sub2002", "案例1-商业繁荣度", "int", "2", "", "busiProsperity", "sub2Arr", 0));
        configurations.put("案例1-房龄", new Configuration("sub2003", "案例1-房龄", "int", "-1", "", "", "sub2Arr", 0));
        configurations.put("案例2-商业繁荣度", new Configuration("sub2004", "案例2-商业繁荣度", "int", "2", "", "busiProsperity", "sub2Arr", 1));
        configurations.put("案例2-房龄", new Configuration("sub2005", "案例2-房龄", "int", "-1", "", "", "sub2Arr", 1));

        return configurations;
    }

    public static final Map<String, Map<String, String>> DICTS = Maps.newHashMap();
    static {
        // 押品分类
        DICTS.put("assetType", ImmutableMap.of("金融类", "Finance", "地产类", "RealEstate"));
        // 是否破产
        DICTS.put("goBroke", ImmutableMap.of("是", "1", "否", "0"));
        // 经营状况
        DICTS.put("status", ImmutableMap.of("开业", "open", "停业", "close"));
        // 商业繁荣度
        DICTS.put("busiProsperity", ImmutableMap.of("市中心", "1", "市区", "2", "郊区", "3"));
    }
}