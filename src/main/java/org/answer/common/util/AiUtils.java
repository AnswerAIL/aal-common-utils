package org.answer.common.util;

import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author Answer.AI.L
 * @date 2019-05-17
 */
public class AiUtils {


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