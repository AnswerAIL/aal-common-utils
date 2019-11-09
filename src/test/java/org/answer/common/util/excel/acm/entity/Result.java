/*
 * Copyright(c) 2015-2019, Answer.AI.L
 * ShenZhen AAL Technology Co., Ltd.
 * All rights reserved.
 *
 * https://github.com/AnswerAIL/
 */
package org.answer.common.util.excel.acm.entity;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *     最终结果
 * </p>
 *
 * @author Jaemon
 * @version 1.0
 * @date 2019-11-09
 */
@Data
public class Result {

    private List<Map<String, Object>> datas;

}