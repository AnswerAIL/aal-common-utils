/*
 * Copyright(c) 2015-2020, AnswerAIL
 * ShenZhen Answer.AI.L Technology Co., Ltd.
 * All rights reserved.
 *
 * <a>https://github.com/AnswerAIL/</a>
 *
 */
package com.jaemon.common.utils;

import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * Class Utils
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 1.0
 */
public class ClassUtils {
    private static final HashMap<String, String> DATA_TYPE_MAPPING = new HashMap<>();;
    static {
        DATA_TYPE_MAPPING.put("java.lang.Byte", "%d"); DATA_TYPE_MAPPING.put("byte", "%d");
        DATA_TYPE_MAPPING.put("java.lang.Short", "%d"); DATA_TYPE_MAPPING.put("short", "%d");
        DATA_TYPE_MAPPING.put("java.lang.Integer", "%d"); DATA_TYPE_MAPPING.put("int", "%d");
        DATA_TYPE_MAPPING.put("java.lang.Long", "%ld"); DATA_TYPE_MAPPING.put("long", "%ld");
        DATA_TYPE_MAPPING.put("java.lang.Float", "%f"); DATA_TYPE_MAPPING.put("float", "%f");
        DATA_TYPE_MAPPING.put("java.lang.Double", "%f"); DATA_TYPE_MAPPING.put("double", "%f");
        DATA_TYPE_MAPPING.put("java.lang.Boolean", "%b"); DATA_TYPE_MAPPING.put("boolean", "%b");
        DATA_TYPE_MAPPING.put("java.lang.Character", "%c"); DATA_TYPE_MAPPING.put("char", "%c");
        DATA_TYPE_MAPPING.put("java.lang.String", "%s");
    }

    /**
     * 获取指定类中所有的私有属性, 包括父类
     *
     * @param clazz clazz
     * @return rlt
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
     * 转json格式字符串(带占位符)
     *
     * @param clazz clazz
     * @return rlt
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