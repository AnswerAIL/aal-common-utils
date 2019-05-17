package org.answer.common.util;

import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Field;

/**
 * @author Answer.AI.L
 * @date 2019-05-17
 */
public class Utils {


    /**
     * 获取指定类中所有的私有属性, 包括父类
     * @param clazz .
     * */
    public static Field[] superField(Class<?> clazz) {
        if (clazz.getSuperclass() != Object.class) {
            Field[] superField = superField(clazz.getSuperclass());
            Field[] currentFields = clazz.getDeclaredFields();

            return ArrayUtils.addAll(superField, currentFields);
        } else {
            return clazz.getDeclaredFields();
        }
    }
}