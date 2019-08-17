/*
 * Copyright(c) 2015-2019, Answer.AI.L
 * ShenZhen AAL Technology Co., Ltd.
 * All rights reserved.
 *
 * https://github.com/AnswerAIL/
 */
package org.answer.common.util.jackjson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Answer.AI.L
 * @version 1.0
 * @date 2019-8-17
 *
 * http://tutorials.jenkov.com/java-json/jackson-objectmapper.html
 * */
public class JacksonUtil {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_COMMENTS, true);

        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        OBJECT_MAPPER.setDateFormat(dateFormat);
    }


    /**
     * 从字符串中读取
     *
     * @param json json 字符串
     * @param clazz 需要转换的类
     * @return .
     * @throws IOException .
     */
    public static <T> T readValue(String json, Class<T> clazz) throws IOException {
        return OBJECT_MAPPER.readValue(json, clazz);
    }

    /**
     * 从字节流中读取
     *
     * @param bytes 字节流
     * @param clazz 需要转换的类
     * @return .
     * @throws IOException .
     */
    public static <T> T readValue(byte[] bytes, Class<T> clazz) throws IOException {
        return OBJECT_MAPPER.readValue(bytes, clazz);
    }

    /**
     * 从文件中读取
     *
     * @param file 文件
     * @param clazz 需要转换的类
     * @return .
     * @throws IOException .
     */
    public static <T> T readValue(File file, Class<T> clazz) throws IOException {
        return OBJECT_MAPPER.readValue(file, clazz);
    }


    /**
     * @param json json 字符串
     * @param clazz 需要转换的类
     * @param parameterClasses clazz 内部泛型类数组
     * @return clazz
     * @throws IOException .
     */
    public static <T> T readValue(String json, Class<T> clazz, Class<?> ... parameterClasses) throws IOException {
        JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructParametricType(clazz, parameterClasses);
        return OBJECT_MAPPER.readValue(json, javaType);
    }


    /**
     * @param json json 字符串
     * @param clazz 需要转换的类
     * @param innerParameterClass clazz 内部泛型类
     * @return clazz
     * @throws IOException .
     */
    public static <T, P> T readValue(String json, Class<T> clazz, Class<P> innerParameterClass) throws IOException {
        JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructParametricType(clazz, innerParameterClass);
        return OBJECT_MAPPER.readValue(json, javaType);
    }

    /**
     * @param jsonArray json 数组字符串
     * @return 对象T的集合
     * @throws IOException .
     */
    public static <T> List<T> readValueList(String jsonArray) throws IOException {
        return OBJECT_MAPPER.readValue(jsonArray, new TypeReference<List<T>>(){});
    }

    /**
     * @param jsonMap json 哈希字符串
     * @return K-V 哈希表
     * @throws IOException .
     */
    public static <K, V> Map<K, V> readValueMap(String jsonMap) throws IOException {
        return OBJECT_MAPPER.readValue(jsonMap, new TypeReference<Map<K, V>>(){});
    }





    /**
     * 写为字符串
     *
     * @param value 值
     * @return rlt
     * */
    public static String writeValueAsString(Object value) throws JsonProcessingException {
        return OBJECT_MAPPER.writeValueAsString(value);
    }

    /**
     * 写为文件
     *
     * @param file 文件
     * @param value 值
     * @throws IOException .
     */
    public static void writeValue(File file, Object value) throws IOException {
        OBJECT_MAPPER.writeValue(file, value);
    }

    /**
     * 写为字节流
     *
     * @param value 值
     * @return .
     * @throws IOException .
     */
    public static byte[] writeValueAsBytes(Object value) throws IOException {
        return OBJECT_MAPPER.writeValueAsBytes(value);
    }
}
