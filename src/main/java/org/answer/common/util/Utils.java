package org.answer.common.util;

import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author Answer.AI.L
 * @date 2019-05-17
 */
public class Utils {


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


    public static String toJsonFormat(Class<?> clazz) {
        return toJsonFormat(clazz, 0);
    }

    /**
     * @param clazz 类
     * @param listLength 对于字段是 {@link List} 类型的, 进行占位符预设时 list 的大小
     * */
    public static String toJsonFormat(Class<?> clazz, int listLength) {
        StringBuilder json = new StringBuilder("{");
        Field[] fields = superDeclaredField(clazz);
        for (Field field : fields) {
            String name = field.getName();
            if (List.class.getName().equals(field.getGenericType().getTypeName())) {
                json.append("\"").append(name).append("\": [");
                for (int i = 0; i < listLength; i++) {
                    json.append("\"%s\",");
                }
                json.deleteCharAt(json.length() - 1);
                json.append("],");
            } else {
                json.append("\"").append(name).append("\": \"%s\",");
            }


        }
        json.deleteCharAt(json.length() - 1);
        json.append("}");

        return json.toString();
    }
}