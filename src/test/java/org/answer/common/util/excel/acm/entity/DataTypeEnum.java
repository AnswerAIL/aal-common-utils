/*
 * Copyright(c) 2015-2019, Answer.AI.L
 * ShenZhen AAL Technology Co., Ltd.
 * All rights reserved.
 *
 * https://github.com/AnswerAIL/
 */
package org.answer.common.util.excel.acm.entity;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    DECIMAL2("decimal2") {
        @Override
        public BigDecimal parse(String value, String defaultValue) {
            try {
                return trans2Number(value, 2);
            } catch (NumberFormatException e) {
                return trans2Number(defaultValue, 2);
            }
        }
    },

    STRING("string") {
        @Override
        public String parse(String value, String defaultValue) {
            return Objects.toString(value, defaultValue);
        }
    },

    DATE("date") {
        @Override
        public String parse(String value, String srcFmt) {
            try {
                return LocalDate.parse(value, DateTimeFormatter.ofPattern(srcFmt)).format(DATE_FMT);
            } catch (Exception e) {
                log.debug("srcFmt=[{}], 日期=[{}]转换fmt=[{}]错误", srcFmt, value, DATE_FMT);
                return value;
            }
        }
    },

    DATETIME("datetime") {
        @Override
        public String parse(String value, String srcFmt) {
            try {
                return LocalDateTime.parse(value, DateTimeFormatter.ofPattern(srcFmt)).format(DATETIME_FMT);
            } catch (Exception e) {
                log.debug("srcFmt=[{}], 日期=[{}]转换fmt=[{}]错误", srcFmt, value, DATETIME_FMT);
                return value;
            }
        }
    }

    ;

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
    private static final DateTimeFormatter DATETIME_FMT = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH时mm分ss秒");


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

    static BigDecimal trans2Number(String value, int scale) {
        return new BigDecimal(value).setScale(scale, BigDecimal.ROUND_HALF_UP);
    }
}