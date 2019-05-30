/*
 * Copyright(c) 2015-2019, Answer.AI.L
 * ShenZhen AAL Technology Co., Ltd.
 * All rights reserved.
 *
 * https://github.com/AnswerAIL/
 */
package org.answer.common.util.iso8583;

import java.lang.annotation.*;

/**
 * <p>
 *     ISO8583字段域注解类
 * </p>
 *
 * Created by Answer on 2017-12-21 09:45
 */
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ISO8583Annotation {

    /**
     * 域索引  MUST
     * */
    int fldIndex();

    /**
     * 数据域长度    MUST
     * */
    int dataFldLength();

    /**
     * 域编码规则(ASCII/BCD/HEX/BINARY)   MUST
     * */
    String encodeRule();

    /**
     * 域字段标识(0: 不用 1: 长度固定 2: 2位变长 3: 3位变长)  MUST
     * */
    String fldFlag();

    /**
     * 域长度编码规则(ASCII/BCD[默认]/HEX/BINARY)
     * */
    String lenEncodeRule() default "BCD";

    /**
     * 域填充规则(NONE(默认)/AFTER/BEFORE)
     * */
    String fillRule() default "NONE";

    /**
     * 域填充字符,十六进制ASSIC码(0:30, 空格:20)
     * */
    String fillChar() default "";

    /**
     * 域默认值
     * */
    String defalutValue() default "";
}