/*
 * Copyright(c) 2015-2019, Answer.AI.L
 * ShenZhen AAL Technology Co., Ltd.
 * All rights reserved.
 *
 * https://github.com/AnswerAIL/
 */
package org.answer.common.util;

import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * @author Answer.AI.L
 * @date 2019-05-17
 */
public class AiUtils {
    private static HashMap<String, String> mapping = new HashMap<>();;
    static {
        mapping.put("java.lang.Byte", "%d"); mapping.put("byte", "%d");
        mapping.put("java.lang.Short", "%d"); mapping.put("short", "%d");
        mapping.put("java.lang.Integer", "%d"); mapping.put("int", "%d");
        mapping.put("java.lang.Long", "%ld"); mapping.put("long", "%ld");
        mapping.put("java.lang.Float", "%f"); mapping.put("float", "%f");
        mapping.put("java.lang.Double", "%f"); mapping.put("double", "%f");
        mapping.put("java.lang.Boolean", "%b"); mapping.put("boolean", "%b");
        mapping.put("java.lang.Character", "%c"); mapping.put("char", "%c");
        mapping.put("java.lang.String", "%s");
    }

    /**
     * 获取指定类中所有的私有属性, 包括父类
     * @param clazz .
     * */
    public static Field[] superDeclaredField(Class<?> clazz) {
        if (clazz.getSuperclass() != Object.class) {
            Field[] superField = superDeclaredField(clazz.getSuperclass());
            Field[] currentFields = clazz.getDeclaredFields();

            return ArrayUtils.addAll(superField, currentFields);
        } else {
            return clazz.getDeclaredFields();
        }
    }


    /**
     * @param clazz 类
     * */
    public static String toJsonFormat(Class<?> clazz) {
        StringBuilder json = new StringBuilder("{");
        Field[] fields = superDeclaredField(clazz);
        for (Field field : fields) {
            String name = field.getName();
            json.append("\"").append(name).append("\": \"%s\",");
        }
        json.deleteCharAt(json.length() - 1);
        json.append("}");

        return json.toString();
    }
}