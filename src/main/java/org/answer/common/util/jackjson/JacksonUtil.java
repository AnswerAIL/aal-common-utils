/*
 * Copyright(c) 2015-2019, Answer.AI.L
 * ShenZhen AAL Technology Co., Ltd.
 * All rights reserved.
 *
 * https://github.com/AnswerAIL/
 */
package org.answer.common.util.jackjson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

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
 *
 * Jackson 配置官方文档: https://github.com/FasterXML/jackson-databind/wiki/JacksonFeatures
 * */
public class JacksonUtil {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /* Jackson 配置 */
    static {
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        OBJECT_MAPPER.setDateFormat(dateFormat);

        /* 建议使用 OBJECT_MAPPER.enable | OBJECT_MAPPER.disable 来设置配置 */
        // 美化输出
        OBJECT_MAPPER.enable(SerializationFeature.INDENT_OUTPUT);

        // 允许序列化空的POJO类(否则会抛出异常)
        OBJECT_MAPPER.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

        // 把 java.util.Date, Calendar 输出为数字（时间戳）
        OBJECT_MAPPER.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // 在遇到未知属性的时候不抛出异常
        OBJECT_MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
//        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // 强制 JSON 空字符串("")转换为 null 对象值:
        OBJECT_MAPPER.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

        // 在 JSON 中允许 C/C++ 样式的注释(非标准，默认禁用)
        OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_COMMENTS, true);

        // 允许没有引号的字段名(非标准)
        OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

        // 允许单引号(非标准)
        OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

        // 强制转义非 ASCII 字符
        OBJECT_MAPPER.configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);

        // 将内容包裹为一个 JSON 属性, 属性名由 @JsonRootName 注解指定
        OBJECT_MAPPER.configure(SerializationFeature.WRAP_ROOT_VALUE, true);

        // 不返回 null 值字段
        OBJECT_MAPPER.setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL);
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
     * json 字符串转 对象(包含内部类)
     *
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
     * json数组字符串 转 List
     *
     * @param jsonArray json 数组字符串
     * @return 对象T的集合
     * @throws IOException .
     */
    public static <T> List<T> readValueList(String jsonArray) throws IOException {
        return OBJECT_MAPPER.readValue(jsonArray, new TypeReference<List<T>>(){});
    }

    /**
     * map 字符串转 map 对象
     *
     * @param jsonMap json 哈希字符串
     * @return K-V 哈希表
     * @throws IOException .
     */
    public static <K, V> Map<K, V> readValueMap(String jsonMap) throws IOException {
        return OBJECT_MAPPER.readValue(jsonMap, new TypeReference<Map<K, V>>(){});
    }


    /**
     * 对象 转 对象
     *
     * @param fromValue 对象参数
     * @param toValueType 转换类型
     * @return rlt
     */
    public static <T> T convertValue(Object fromValue, Class<T> toValueType) {
        return OBJECT_MAPPER.convertValue(fromValue, toValueType);
    }



    /**
     * 对象 转 json 字符串
     *
     * @param value 值
     * @return rlt
     * */
    public static String writeValueAsString(Object value) throws JsonProcessingException {
        return OBJECT_MAPPER.writeValueAsString(value);
    }

    /**
     * 对象 转 文件
     *
     * @param file 文件
     * @param value 值
     * @throws IOException .
     */
    public static void writeValue(File file, Object value) throws IOException {
        OBJECT_MAPPER.writeValue(file, value);
    }

    /**
     * 对象 转 字节数组
     *
     * @param value 值
     * @return .
     * @throws IOException .
     */
    public static byte[] writeValueAsBytes(Object value) throws IOException {
        return OBJECT_MAPPER.writeValueAsBytes(value);
    }
}
