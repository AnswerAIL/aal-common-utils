package org.answer.common.util.iso8583;

import org.answer.common.util.exception.AIException;
import org.answer.common.util.number.NumberStringUtil;
import org.apache.commons.lang3.StringUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * ISO8583报文组/解包工具类
 * Created by Answer on 2017-12-21 09:23
 */
public class TransISO8583MessageUtil {
    private final static String CHARSET = "GBK";

    /** 不用 */
    private final static String FLD_LENGTH_FLAG_0 = "0";
    /** 长度固定 */
    private final static String FLD_LENGTH_FLAG_1 = "1";
    /** 2位变长 */
    private final static String FLD_LENGTH_FLAG_2 = "2";
    /** 3位变长 */
    private final static String FLD_LENGTH_FLAG_3 = "3";

    /** BCD */
    private final static String ENCODE_RULE_BCD = "BCD";
    /** HEX */
    private final static String ENCODE_RULE_HEX = "HEX";
    /** BINARY */
    private final static String ENCODE_RULE_BINARY = "BINARY";

    /** 前补 */
    private final static String FILL_RULE_BEFORE = "BEFORE";
    /** 后补 */
    private final static String FILL_RULE_AFTER = "AFTER";

    /** ASCII */
    private final static String LEN_ENCODE_RULE_ASCII = "ASCII";
    /** BCD */
    private final static String LEN_ENCODE_RULE_BCD = "BCD";
    /** HEX */
    private final static String LEN_ENCODE_RULE_HEX = "HEX";
    /** BINARY */
    private final static String LEN_ENCODE_RULE_BINARY = "BINARY";


    /**
     * ISO8583报文组包
     * @param obj               组包对象
     * @param bitmap            位图(十六进制表示法)
     * @param tpdu              tpdu
     * @param msgHead           报文头
     * @param msgType           消息类型
     * @exception Exception .
     * */
    public static String packISO8583(Object obj, String bitmap, String tpdu, String msgHead, String msgType)
            throws Exception {
        StringBuilder sendMsg = new StringBuilder();
        sendMsg.append(tpdu).append(msgHead).append(msgType).append(bitmap);
        // 位图十六进制转二进制
        bitmap = NumberStringUtil.byteToBinary(NumberStringUtil.hexStringToBytes(bitmap));
        Map<Integer, String> iso8583FldMap = obtISO8583FldMap(obj.getClass());

        String currentFld = "";
        for (int i = 0; i < bitmap.length(); i++) {
            if ("1".equals(bitmap.substring(i, i + 1))) {
                currentFld = iso8583FldMap.get(i + 1);
                String fldHexValue = getFldHexValue(obj, currentFld);
                sendMsg.append(fldHexValue);
            }
        }
        // 计算报文长度
        sendMsg.insert(0, NumberStringUtil.addLeftZero(Integer.toHexString(NumberStringUtil.hexStringToBytes(sendMsg.toString()).length).toUpperCase(), 4));

        return sendMsg.toString();
    }


    /**
     * ISO8583报文解包
     * @param clazz             解包对象的类名
     * @param recvMsg           接收报文字符串
     * @return Object           返回解包生成的对象信息
     * @exception Exception .
     * */
    public static Object unpackISO8583(Class clazz, String recvMsg)
            throws Exception {
        return unpackISO8583(clazz.getName(), recvMsg);
    }


    /**
     * ISO8583报文解包
     * @param className         解包对象的类名
     * @param recvMsg           接收报文字符串
     * @return Object           返回解包生成的对象信息
     * @exception Exception .
     * */
    public static Object unpackISO8583(String className, String recvMsg)
            throws Exception {
        recvMsg = recvMsg.substring(30, recvMsg.length());
        byte[] recvMsgByte = NumberStringUtil.hexStringToBytes(recvMsg);
        // 字节数据索引位置
        int index = 0;
        int[] indexArr = new int[1];
        // 位元表,获取前八个字节
        byte[] bitTable = NumberStringUtil.getBytes(recvMsgByte, index, 8);
        String bitTableBinary = NumberStringUtil.byteToBinary(bitTable);
        // 判断第一位如果是0,则为64域,为1则为128域(需再获取8个字节)
        if ("1".equals(bitTableBinary.substring(0, 1))) {
            bitTable = NumberStringUtil.getBytes(recvMsgByte, index, 16);
            index += 16;
            bitTableBinary = NumberStringUtil.byteToBinary(bitTable);
        } else {
            index += 8;
        }
        Class<?> clazz = Class.forName(className);
        Object obj = clazz.newInstance();
        Map<Integer, String> iso8583FldMap = obtISO8583FldMap(clazz);

        String currentFld;
        indexArr[0] = index;
        for (int i = 1; i < bitTableBinary.length(); i++) {
            // 位元表值等1的说明存在该域
            if ("1".equals(bitTableBinary.substring(i, i + 1))) {
                currentFld = iso8583FldMap.get(i + 1);
                String fldValue = getFldValue(clazz, currentFld, recvMsgByte, indexArr);
                setFldValue(obj, currentFld, fldValue);
            }
        }
        return obj;
    }


    /**
     * 获取实体类中位索引域和字段的关系表 PACK&UNPACK
     * @param clazz             类名
     * @exception ClassNotFoundException .
     * */
    private static Map<Integer, String> obtISO8583FldMap(Class clazz)
            throws ClassNotFoundException {
        Map<Integer, String> map = new HashMap<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            boolean fldHasAnnotation = field.isAnnotationPresent(ISO8583Annotation.class);
            if (fldHasAnnotation) {
                ISO8583Annotation fldAnnotation = field.getAnnotation(ISO8583Annotation.class);
                map.put(fldAnnotation.fldIndex(), field.getName());
            }
        }
        return map;
    }


    /**
     * 字段值转十六进制字符串 PACK
     * @param obj               组包对象
     * @param fldName           字段名
     * @exception Exception .
     * */
    private static String getFldHexValue(Object obj, String fldName)
            throws Exception {
        PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fldName, obj.getClass());
        String fldValue = (String) propertyDescriptor.getReadMethod().invoke(obj);

        Field field = obj.getClass().getDeclaredField(fldName);
        boolean fldHasAnnotation = field.isAnnotationPresent(ISO8583Annotation.class);
        if (fldHasAnnotation) {
            ISO8583Annotation fldAnnotation = field.getAnnotation(ISO8583Annotation.class);
            String defaultValue = fldAnnotation.defalutValue();
            // 如果设置了默认值, 使用默认值
            if (StringUtils.isNotEmpty(defaultValue)) {
                fldValue = defaultValue;
            } else {
                fldValue = transToHexValue(fldAnnotation, fldValue);
            }
        }
        return fldValue;
    }


    /**
     * 组包时根据字段原值按照其配置规则转为十六进制 PACK
     * @param iso8583Annotation ISO8583字段包含的注解对象信息
     * @param fldValue          字段值
     * @exception Exception .
     * */
    private static String transToHexValue(ISO8583Annotation iso8583Annotation, String fldValue)
            throws Exception {
        byte[] fldValueBytes = null;
        String fldFlag = iso8583Annotation.fldFlag();
        String encodeRule = iso8583Annotation.encodeRule();
        int dataLength = iso8583Annotation.dataFldLength();
        String fillRule = iso8583Annotation.fillRule();
        String fillChar = iso8583Annotation.fillChar();
        String lenEncodeRule = iso8583Annotation.lenEncodeRule();

        // 固定长度
        if (FLD_LENGTH_FLAG_1.equals(fldFlag)) {
            int dataLen = (ENCODE_RULE_BINARY.equals(encodeRule)) ? dataLength * 2 : dataLength;
            fldValue = NumberStringUtil.getByteLengthStr(fldValue, dataLen, CHARSET);

            // 前补
            if (FILL_RULE_BEFORE.equals(fillRule)) {
                String lpad = new String(NumberStringUtil.hexStringToBytes(fillChar), CHARSET);
                fldValue = NumberStringUtil.addLeftChar(fldValue, dataLength, lpad.charAt(0), CHARSET);
            // 后补
            } else if (FILL_RULE_AFTER.equals(fillRule)) {
                String rpad = new String(NumberStringUtil.hexStringToBytes(fillChar), CHARSET);
                fldValue = NumberStringUtil.addRightChar(fldValue, dataLength, rpad.charAt(0), CHARSET);
            }

            // 根据域编码规则,把数据转换
            fldValueBytes = fieldEncodeRule(fldValue, CHARSET, encodeRule);

            // 2位和3位变长
        } else if (FLD_LENGTH_FLAG_2.equals(fldFlag) || FLD_LENGTH_FLAG_3.equals(fldFlag)) {
            int fixLength = fldValue.getBytes(CHARSET).length;
            if (ENCODE_RULE_BINARY.equals(encodeRule)) {
                fixLength = (fixLength + 1) / 2;
            }
            int fldLenSize = (FLD_LENGTH_FLAG_2.equals(fldFlag)) ? 1 : 2;

            byte[] lengthByte = null;
            // 根据域长度编码规则,把数据长度头转换
            if (LEN_ENCODE_RULE_ASCII.equals(lenEncodeRule)) {
                lengthByte = NumberStringUtil.addLeftZero(
                        Integer.toString(fixLength), Integer.parseInt(Integer.toString(fldLenSize))).getBytes(CHARSET);
            } else if (LEN_ENCODE_RULE_BCD.equals(lenEncodeRule)) {
                int fldLenSizeTmp = fldLenSize * 2;
                lengthByte = NumberStringUtil.hexStringToBytes(
                        NumberStringUtil.addLeftZero(Integer.toString(fixLength), fldLenSizeTmp));
            } else if (LEN_ENCODE_RULE_HEX.equals(lenEncodeRule)) {
                int fldLenSizeTmp = fldLenSize * 2;
                lengthByte = NumberStringUtil.hexStringToBytes(
                        NumberStringUtil.addLeftZero(Integer.toHexString(fixLength), fldLenSizeTmp));
            } else {
                // 没有域长度编码规则信息抛异常
                throw new AIException("[AI]Error of lenEncodeRule.");
            }
            // 根据域编码规则,把数据转换
            fldValueBytes = fieldEncodeRule(fldValue, CHARSET, encodeRule);
            fldValueBytes = NumberStringUtil.byteArrayAdd(lengthByte, fldValueBytes);
        } else if (FLD_LENGTH_FLAG_0.equals(fldFlag)) {
            // TODO Answer 暂不做处理
        } else {
            throw new AIException("[AI]Error of fldFlag.");
        }
        return NumberStringUtil.bytesToHexString(fldValueBytes);
    }


    /**
     * 根据域编码规则,把数据转换 PACK
     * @param fldValue          字段值
     * @param charset           编码格式
     * @param fldEncodeRule     字段编码规则
     * @exception Exception .
     */
    private static byte[] fieldEncodeRule(String fldValue, String charset, String fldEncodeRule)
            throws Exception{
        byte[] fieldByte;
        if (ENCODE_RULE_BCD.equals(fldEncodeRule)) {
            if (fldValue.length() % 2 != 0) {
                fldValue = fldValue + "0";
            }
            fieldByte = NumberStringUtil.hexStringToBytes(fldValue);
        } else if (ENCODE_RULE_HEX.equals(fldEncodeRule)) {
            String fieldHexStr = Integer.toHexString(Integer.parseInt(fldValue));
            if (fieldHexStr.length() % 2 != 0)  {
                fieldHexStr = fieldHexStr + "0";
            }
            fieldByte = NumberStringUtil.hexStringToBytes(fieldHexStr);
        } else if (ENCODE_RULE_BINARY.equals(fldEncodeRule)) {
            if (fldValue.length() % 2 != 0) {
                fldValue = fldValue + "0";
            }
            fieldByte = NumberStringUtil.hexStringToBytes(fldValue);
        } else {
            //默认ASSIC编码 ENCODE_RULE_ASCII
            fieldByte = fldValue.getBytes(charset);
        }
        return fieldByte;
    }


    /**
     * 获取解码后的字段值 UNPACK
     * @param clazz             类名
     * @param fldName           字段名
     * @param byteArray         报文字节数组
     * @param indexArr          字段所在报文的索引位置
     * @exception Exception .
     * */
    private static String getFldValue(Class clazz, String fldName, byte[] byteArray, int[] indexArr)
            throws Exception {
        String transFldValue = "";
        Field field = clazz.getDeclaredField(fldName);
        boolean fldHasAnnotation = field.isAnnotationPresent(ISO8583Annotation.class);
        if (fldHasAnnotation) {
            ISO8583Annotation fldAnnotation = field.getAnnotation(ISO8583Annotation.class);
            int dataLength = fldAnnotation.dataFldLength();
            String lenEncodeRule = fldAnnotation.lenEncodeRule();
            String fldFlag = fldAnnotation.fldFlag();
            String encodeRule = fldAnnotation.encodeRule();

            // 字段长度固定
            if (FLD_LENGTH_FLAG_1.equals(fldFlag)) {
                byte[] fieldByte;
                if (ENCODE_RULE_BCD.equals(encodeRule)) {
                    int fldLen = (dataLength + 1) / 2;
                    fieldByte = NumberStringUtil.getBytes(byteArray, indexArr[0], fldLen);
                    indexArr[0] += fldLen;
                    transFldValue = fieldDecodeRule(fieldByte, CHARSET, encodeRule);
                    // 截取字段长度
                    transFldValue = transFldValue.substring(0, dataLength);
                } else {
                    fieldByte = NumberStringUtil.getBytes(byteArray, indexArr[0], dataLength);
                    indexArr[0] += dataLength;
                    transFldValue = fieldDecodeRule(fieldByte, CHARSET, encodeRule);
                }
            // 字段长度可变长度
            } else if (FLD_LENGTH_FLAG_2.equals(fldFlag) || FLD_LENGTH_FLAG_3.equals(fldFlag)) {
                int lenSize = Integer.parseInt(fldFlag);
                lenSize = (lenSize + 1) / 2;
                byte[] fldLenByte = NumberStringUtil.getBytes(byteArray, indexArr[0], lenSize);
                indexArr[0] += lenSize;
                int fieldLength = fieldLenDecodeRule(fldLenByte, lenEncodeRule);
                // 编码规则是BCD
                if (ENCODE_RULE_BCD.equals(encodeRule)) {
                    byte[] fieldByte = NumberStringUtil.getBytes(byteArray, indexArr[0], (fieldLength + 1) / 2);
                    indexArr[0] += (fieldLength + 1) / 2;
                    transFldValue = fieldDecodeRule(fieldByte, CHARSET, encodeRule);
                    //左靠
                    transFldValue = transFldValue.substring(0, fieldLength);
                } else {
                    byte[] fieldByte = NumberStringUtil.getBytes(byteArray, indexArr[0], fieldLength);
                    indexArr[0] += fieldLength;
                    transFldValue = fieldDecodeRule(fieldByte, CHARSET, encodeRule);
                }
            } else if (FLD_LENGTH_FLAG_0.equals(fldFlag)) {
                // TODO Answer 暂不做处理
            } else {
                throw new AIException("[AI]Error of fldFlag.");
            }

        }
        return transFldValue;
    }


    /**
     * 把解码后的值注入到对象obj中 UNPACK
     * @param obj               对象
     * @param fldName           字段名称
     * @param fldValue          字段值
     * @exception Exception .
     * */
    private static void setFldValue(Object obj, String fldName, String fldValue)
            throws Exception {
        fldName = (fldName.substring(0, 1)).toUpperCase() + fldName.substring(1);
        PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fldName, obj.getClass());
        propertyDescriptor.getWriteMethod().invoke(obj, fldValue);
    }


    /**
     * 根据域编码规则,把数据转换 UNPACK
     * @param fieldByte         字段值(字节数组)
     * @param charset           编码格式
     * @param fldEncodeRule     编码规则
     * @exception Exception .
     * */
    private static String fieldDecodeRule(byte[] fieldByte, String charset, String fldEncodeRule)
            throws Exception{
        String fieldValue;
        if (ENCODE_RULE_BCD.equals(fldEncodeRule)) {
            fieldValue = NumberStringUtil.bytesToHexString(fieldByte);
        } else if (ENCODE_RULE_HEX.equals(fldEncodeRule)) {
            fieldValue = NumberStringUtil.bytesToHexString(fieldByte);
            fieldValue = Integer.toString(Integer.parseInt(fieldValue, 16));
        } else if (ENCODE_RULE_BINARY.equals(fldEncodeRule)) {
            fieldValue = NumberStringUtil.bytesToHexString(fieldByte);
        } else {  // 默认 ENCODE_RULE_ASCII
            fieldValue = new String(fieldByte, charset);
        }
        return fieldValue.trim();
    }


    /**
     * 根据域长度编码规则,把数据转换 UNPACK
     * @param fldLenByte        变长字段的长度(字节数组)
     * @param fldLenEncRule     域长度编码规则(针对变长字段的实际长度)
     * @exception Exception .
     */
    private static int fieldLenDecodeRule(byte[] fldLenByte, String fldLenEncRule)
            throws Exception{
        int fieldLength = 0;

        //根据域长度编码规则,把数据长度头转换
        if (LEN_ENCODE_RULE_ASCII.equals(fldLenEncRule)) {
            fieldLength = Integer.parseInt(new String(fldLenByte), 10);
        } else if (LEN_ENCODE_RULE_BCD.equals(fldLenEncRule)) {
            fieldLength = Integer.parseInt(NumberStringUtil.bytesToHexString(fldLenByte), 10);
        } else if (LEN_ENCODE_RULE_HEX.equals(fldLenEncRule)) {
            fieldLength = Integer.parseInt(NumberStringUtil.bytesToHexString(fldLenByte), 16);
        } else if (LEN_ENCODE_RULE_BINARY.equals(fldLenEncRule)) {
            fieldLength = Integer.parseInt(NumberStringUtil.byteToBinary(fldLenByte), 10);
        } else {
            // 没有域长度编码规则信息 抛异常
            throw new AIException("[AI]Error of fieldLenDecodeRule.");
        }
        return fieldLength;
    }



    public static void main(String[] args) throws Exception {
        /*
        final String TPDU = "6000000000";
        final String HEAD = "612200000000";
        String recvMsg = "00BA600000000061220000000003007020048000C1805B193104890100000006059063000000000000010000018298009130383030303030332020202020202020202030303038303030303030303030303100040000313536005553560118FFFFFFFFFFFF03104890100000006059FFFFFFFF0101000200000064020000080000032017122310225672FB47880000012B01001141000006000000120000060001810016303030303030303030303030303030304435373336384239";
        AIISO8583DTO aiISO8583DTO = (AIISO8583DTO) TransISO8583MessageUtil.unpackISO8583(AIISO8583DTO.class, recvMsg);
        System.out.println("UNPACK ISO8583: " + JSON.toJSONString(aiISO8583DTO));

        String maxBitmap = "7020048000C1805B";
        String sendMsg = TransISO8583MessageUtil.packISO8583(aiISO8583DTO, maxBitmap, TPDU, HEAD, "0300");
        System.out.println("PACK ISO8583: " + sendMsg);
        */
    }

}