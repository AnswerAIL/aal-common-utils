/*
 * Copyright(c) 2015-2019, Answer.AI.L
 * ShenZhen AAL Technology Co., Ltd.
 * All rights reserved.
 *
 * https://github.com/AnswerAIL/
 */
package org.answer.common.util.excel.acm.entity;

import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * <p>
 *     数据类型枚举
 * </p>
 *
 * @author Jaemon
 * @version 1.0
 * @date 2019-11-09
 */
@Slf4j
public enum DataTypeEnum implements IDataType {

    INT("int") {
        @Override
        public Integer parse(String value, String defaultValue) {
            try {
                if (value.contains(".")) {
                    value = transValue(value);
                }
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                return Integer.valueOf(defaultValue);
            }
        }
    },

    LONG("long") {
        @Override
        public Long parse(String value, String defaultValue) {
            try {
                if (value.contains(".")) {
                    value = transValue(value);
                }
                return Long.parseLong(value);
            } catch (NumberFormatException e) {
                return Long.valueOf(defaultValue);
            }
        }
    },

    FLOAT("float") {
        @Override
        public Float parse(String value, String defaultValue) {
            try {
                return Float.parseFloat(value);
            } catch (NumberFormatException e) {
                return Float.valueOf(defaultValue);
            }
        }
    },

    STRING("string") {
        @Override
        public String parse(String value, String defaultValue) {
            return Objects.toString(value, defaultValue);
        }
    },

    ;


    private String value;

    DataTypeEnum(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    @Override
    public Object parse(String value, String defaultValue) {
        throw new IllegalArgumentException("");
    }


    static String transValue(String value) {
        if (log.isDebugEnabled()) {
            log.debug("转换前value={}", value);
        }
        value = value.substring(0, value.indexOf("."));
        if (log.isDebugEnabled()) {
            log.debug("转换后value={}", value);
        }
        return value;
    }
}