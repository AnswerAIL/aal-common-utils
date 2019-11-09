/*
 * Copyright(c) 2015-2019, Answer.AI.L
 * ShenZhen AAL Technology Co., Ltd.
 * All rights reserved.
 *
 * https://github.com/AnswerAIL/
 */
package org.answer.common.util.excel.acm.entity;

/**
 * <p>
 *     转换接口
 * </p>
 *
 * @author Jaemon
 * @version 1.0
 * @date 2019-11-09
 */
public interface IDataType<T> {
    T parse(String value, String defaultValue);
}