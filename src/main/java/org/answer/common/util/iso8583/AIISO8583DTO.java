/*
 * Copyright(c) 2015-2019, Answer.AI.L
 * ShenZhen AAL Technology Co., Ltd.
 * All rights reserved.
 *
 * https://github.com/AnswerAIL/
 */
package org.answer.common.util.iso8583;

/**
 * <p>
 *     报文交互DTO
 * </p>
 *
 * Created by Answer on 2017-12-23 10:01
 */
public class AIISO8583DTO {

    /** 2域-主账号 */
    @ISO8583Annotation(
            fldIndex = 2, dataFldLength = 19, encodeRule = "BCD", fldFlag = "2"
    )
    private String cardNo02;

    /** 3域-交易处理码 */
    @ISO8583Annotation(
            fldIndex = 3, dataFldLength = 6, encodeRule = "BCD", fldFlag = "1",
            fillRule = "BEFORE", fillChar = "30"
    )
    private String transProcCode03;

    /** 4域-交易金额 */
    @ISO8583Annotation(
            fldIndex = 4, dataFldLength = 12, encodeRule = "BCD", fldFlag = "1",
            fillRule = "BEFORE", fillChar = "30"
    )
    private String transAmt04;

    /** 11域-受卡方系统跟踪号 */
    @ISO8583Annotation(
            fldIndex = 11, dataFldLength = 6, encodeRule = "BCD", fldFlag = "1",
            fillRule = "BEFORE", fillChar = "30"
    )
    private String sysTrackNo11;

    /** 12域-受卡方所在地时间 */
    @ISO8583Annotation(
            fldIndex = 12, dataFldLength = 6, encodeRule = "BCD", fldFlag = "1",
            fillRule = "BEFORE", fillChar = "30"
    )
    private String locTime12;

    /** 13域-受卡方所在地日期 */
    @ISO8583Annotation(
            fldIndex = 13, dataFldLength = 4, encodeRule = "BCD", fldFlag = "1",
            fillRule = "BEFORE", fillChar = "30"
    )
    private String locDate13;

    /** 14域-卡有效期 */
    @ISO8583Annotation(
            fldIndex = 14, dataFldLength = 4, encodeRule = "BCD", fldFlag = "1",
            fillRule = "BEFORE", fillChar = "30"
    )
    private String expireDate14;

    /** 15域-清算日期 */
    @ISO8583Annotation(
            fldIndex = 15, dataFldLength = 4, encodeRule = "BCD", fldFlag = "1",
            fillRule = "BEFORE", fillChar = "30"
    )
    private String liquidatimeDate15;

    /** 22域-服务点输入方式码 */
    @ISO8583Annotation(
            fldIndex = 22, dataFldLength = 3, encodeRule = "BCD", fldFlag = "1",
            fillRule = "BEFORE", fillChar = "30"
    )
    private String serviceInputModeCode22;

    /** 23域-卡片序列号 */
    @ISO8583Annotation(
            fldIndex = 23, dataFldLength = 3, encodeRule = "BCD", fldFlag = "1",
            fillRule = "BEFORE", fillChar = "30"
    )
    private String cardSerialNo23;

    /** 25域-服务点条件码 */
    @ISO8583Annotation(
            fldIndex = 25, dataFldLength = 2, encodeRule = "BCD", fldFlag = "1",
            fillRule = "BEFORE", fillChar = "30"
    )
    private String serviceConditionCode25;

    /** 26域-服务点PIN获取码 */
    @ISO8583Annotation(
            fldIndex = 26, dataFldLength = 2, encodeRule = "BCD", fldFlag = "1",
            fillRule = "BEFORE", fillChar = "30"
    )
    private String servicePINAccessCode26;

    /** 32域-受理方标识码 */
    @ISO8583Annotation(
            fldIndex = 32, dataFldLength = 11, encodeRule = "BCD", fldFlag = "2",
            defalutValue = ""
    )
    private String acquirerMarkCode32;

    /** 35域-2磁道数据 */
    @ISO8583Annotation(
            fldIndex = 35, dataFldLength = 37, encodeRule = "BCD", fldFlag = "2"
    )
    private String track2Data35;

    /** 36域-3磁道数据 */
    @ISO8583Annotation(
            fldIndex = 36, dataFldLength = 999, encodeRule = "BCD", fldFlag = "3"
    )
    private String track3Data36;

    /** 37域-检索参考号 */
    @ISO8583Annotation(
            fldIndex = 37, dataFldLength = 12, encodeRule = "ASCII", fldFlag = "1",
            fillRule = "AFTER", fillChar = "20"
    )
    private String retrievalReferenceNo37;

    /** 38域-授权标识应答码 */
    @ISO8583Annotation(
            fldIndex = 38, dataFldLength = 6, encodeRule = "ASCII", fldFlag = "1",
            fillRule = "AFTER", fillChar = "20"
    )
    private String authIdentityRespCode38;

    /** 39域-应答码 */
    @ISO8583Annotation(
            fldIndex = 39, dataFldLength = 2, encodeRule = "ASCII", fldFlag = "1",
            fillRule = "AFTER", fillChar = "20"
    )
    private String respCode39;

    /** 40域-应答描述 */
    @ISO8583Annotation(
            fldIndex = 40, dataFldLength = 99, encodeRule = "ASCII", fldFlag = "2"
    )
    private String respDesc40;

    /** 41域-受卡机终端标识码 */
    @ISO8583Annotation(
            fldIndex = 41, dataFldLength = 18, encodeRule = "ASCII", fldFlag = "1",
            fillRule = "AFTER", fillChar = "20"
    )
    private String cardAcceptorTerminalID41;

    /** 42域-受卡方标识码 */
    @ISO8583Annotation(
            fldIndex = 42, dataFldLength = 15, encodeRule = "ASCII", fldFlag = "1",
            fillRule = "AFTER", fillChar = "20"
    )
    private String cardAcceptorID42;

    /** 44域-附加响应数据 */
    @ISO8583Annotation(
            fldIndex = 44, dataFldLength = 25, encodeRule = "ASCII", fldFlag = "2"
    )
    private String additionalRespData44;

    /** 48域-附加数据-私有 */
    @ISO8583Annotation(
            fldIndex = 48, dataFldLength = 322, encodeRule = "BCD", fldFlag = "3"
    )
    private String additionalDataPrivate48;

    /** 49域-交易货币代码 */
    @ISO8583Annotation(
            fldIndex = 49, dataFldLength = 3, encodeRule = "ASCII", fldFlag = "1",
            fillRule = "AFTER", fillChar = "20"
    )
    private String currencyCode49;

    /** 52域-个人标识码数据 */
    @ISO8583Annotation(
            fldIndex = 52, dataFldLength = 8, encodeRule = "BINARY", fldFlag = "1",
            fillRule = "BEFORE", fillChar = "30"
    )
    private String personalIDCodeData52;

    /** 53域-安全控制信息 */
    @ISO8583Annotation(
            fldIndex = 53, dataFldLength = 16, encodeRule = "BCD", fldFlag = "1",
            fillRule = "BEFORE", fillChar = "30"
    )
    private String safeControlInfo53;

    /** 54域-附加金额 */
    @ISO8583Annotation(
            fldIndex = 54, dataFldLength = 256, encodeRule = "ASCII", fldFlag = "3"
    )
    private String additionalAmt54;

    /** 55域-IC卡数据域 */
    @ISO8583Annotation(
            fldIndex = 55, dataFldLength = 512, encodeRule = "ASCII", fldFlag = "3"
    )
    private String ICCardDataDomain55;

    /** 57域-自定义域 */
    @ISO8583Annotation(
            fldIndex = 57, dataFldLength = 999, encodeRule = "ASCII", fldFlag = "3"
    )
    private String fld57Domain57;

    /** 58域-PBOC电子钱包/存折标准的交易信息 */
    @ISO8583Annotation(
            fldIndex = 58, dataFldLength = 512, encodeRule = "BINARY", fldFlag = "3"
    )
    private String eWalletTransInfo58;

    /** 60域-自定义域 */
    @ISO8583Annotation(
            fldIndex = 60, dataFldLength = 17, encodeRule = "BCD", fldFlag = "3"
    )
    private String fld60Domain60;

    /** 61域-原始信息域 */
    @ISO8583Annotation(
            fldIndex = 61, dataFldLength = 29, encodeRule = "BCD", fldFlag = "3"
    )
    private String originalInfoDomain61;

    /** 62域-自定义域 */
    @ISO8583Annotation(
            fldIndex = 62, dataFldLength = 999, encodeRule = "BINARY", fldFlag = "3"
    )
    private String fld62Domain62;

    /** 63域-自定义域 */
    @ISO8583Annotation(
            fldIndex = 63, dataFldLength = 512, encodeRule = "ASCII", fldFlag = "3"
    )
    private String fld63Domain63;

    /** 64域-MAC */
    @ISO8583Annotation(
            fldIndex = 64, dataFldLength = 8, encodeRule = "ASCII", fldFlag = "1",
            fillRule = "BEFORE", fillChar = "20", defalutValue = "0000000000000000"
    )
    private String mac64;

    /** 2域-主账号 */
    public String getCardNo02() {
        return cardNo02;
    }

    /** 2域-主账号 */
    public void setCardNo02(String cardNo02) {
        this.cardNo02 = cardNo02;
    }

    /** 3域-交易处理码 */
    public String getTransProcCode03() {
        return transProcCode03;
    }

    /** 3域-交易处理码 */
    public void setTransProcCode03(String transProcCode03) {
        this.transProcCode03 = transProcCode03;
    }

    /** 4域-交易金额 */
    public String getTransAmt04() {
        return transAmt04;
    }

    /** 4域-交易金额 */
    public void setTransAmt04(String transAmt04) {
        this.transAmt04 = transAmt04;
    }

    /** 11域-受卡方系统跟踪号 */
    public String getSysTrackNo11() {
        return sysTrackNo11;
    }

    /** 11域-受卡方系统跟踪号 */
    public void setSysTrackNo11(String sysTrackNo11) {
        this.sysTrackNo11 = sysTrackNo11;
    }

    /** 12域-受卡方所在地时间 */
    public String getLocTime12() {
        return locTime12;
    }

    /** 12域-受卡方所在地时间 */
    public void setLocTime12(String locTime12) {
        this.locTime12 = locTime12;
    }

    /** 13域-受卡方所在地日期 */
    public String getLocDate13() {
        return locDate13;
    }

    /** 13域-受卡方所在地日期 */
    public void setLocDate13(String locDate13) {
        this.locDate13 = locDate13;
    }

    /** 14域-卡有效期 */
    public String getExpireDate14() {
        return expireDate14;
    }

    /** 14域-卡有效期 */
    public void setExpireDate14(String expireDate14) {
        this.expireDate14 = expireDate14;
    }

    /** 15域-清算日期 */
    public String getLiquidatimeDate15() {
        return liquidatimeDate15;
    }

    /** 15域-清算日期 */
    public void setLiquidatimeDate15(String liquidatimeDate15) {
        this.liquidatimeDate15 = liquidatimeDate15;
    }

    /** 22域-服务点输入方式码 */
    public String getServiceInputModeCode22() {
        return serviceInputModeCode22;
    }

    /** 22域-服务点输入方式码 */
    public void setServiceInputModeCode22(String serviceInputModeCode22) {
        this.serviceInputModeCode22 = serviceInputModeCode22;
    }

    /** 23域-卡片序列号 */
    public String getCardSerialNo23() {
        return cardSerialNo23;
    }

    /** 23域-卡片序列号 */
    public void setCardSerialNo23(String cardSerialNo23) {
        this.cardSerialNo23 = cardSerialNo23;
    }

    /** 25域-服务点条件码 */
    public String getServiceConditionCode25() {
        return serviceConditionCode25;
    }

    /** 25域-服务点条件码 */
    public void setServiceConditionCode25(String serviceConditionCode25) {
        this.serviceConditionCode25 = serviceConditionCode25;
    }

    /** 26域-服务点PIN获取码 */
    public String getServicePINAccessCode26() {
        return servicePINAccessCode26;
    }

    /** 26域-服务点PIN获取码 */
    public void setServicePINAccessCode26(String servicePINAccessCode26) {
        this.servicePINAccessCode26 = servicePINAccessCode26;
    }

    /** 32域-受理方标识码 */
    public String getAcquirerMarkCode32() {
        return acquirerMarkCode32;
    }

    /** 32域-受理方标识码 */
    public void setAcquirerMarkCode32(String acquirerMarkCode32) {
        this.acquirerMarkCode32 = acquirerMarkCode32;
    }

    /** 35域-2磁道数据 */
    public String getTrack2Data35() {
        return track2Data35;
    }

    /** 35域-2磁道数据 */
    public void setTrack2Data35(String track2Data35) {
        this.track2Data35 = track2Data35;
    }

    /** 36域-3磁道数据 */
    public String getTrack3Data36() {
        return track3Data36;
    }

    /** 36域-3磁道数据 */
    public void setTrack3Data36(String track3Data36) {
        this.track3Data36 = track3Data36;
    }

    /** 37域-检索参考号 */
    public String getRetrievalReferenceNo37() {
        return retrievalReferenceNo37;
    }

    /** 37域-检索参考号 */
    public void setRetrievalReferenceNo37(String retrievalReferenceNo37) {
        this.retrievalReferenceNo37 = retrievalReferenceNo37;
    }

    /** 38域-授权标识应答码 */
    public String getAuthIdentityRespCode38() {
        return authIdentityRespCode38;
    }

    /** 38域-授权标识应答码 */
    public void setAuthIdentityRespCode38(String authIdentityRespCode38) {
        this.authIdentityRespCode38 = authIdentityRespCode38;
    }

    /** 39域-应答码 */
    public String getRespCode39() {
        return respCode39;
    }

    /** 39域-应答码 */
    public void setRespCode39(String respCode39) {
        this.respCode39 = respCode39;
    }

    /** 40域-应答描述 */
    public String getRespDesc40() {
        return respDesc40;
    }

    /** 40域-应答描述 */
    public void setRespDesc40(String respDesc40) {
        this.respDesc40 = respDesc40;
    }

    /** 41域-受卡机终端标识码 */
    public String getCardAcceptorTerminalID41() {
        return cardAcceptorTerminalID41;
    }

    /** 41域-受卡机终端标识码 */
    public void setCardAcceptorTerminalID41(String cardAcceptorTerminalID41) {
        this.cardAcceptorTerminalID41 = cardAcceptorTerminalID41;
    }

    /** 42域-受卡方标识码 */
    public String getCardAcceptorID42() {
        return cardAcceptorID42;
    }

    /** 42域-受卡方标识码 */
    public void setCardAcceptorID42(String cardAcceptorID42) {
        this.cardAcceptorID42 = cardAcceptorID42;
    }

    /** 44域-附加响应数据 */
    public String getAdditionalRespData44() {
        return additionalRespData44;
    }

    /** 44域-附加响应数据 */
    public void setAdditionalRespData44(String additionalRespData44) {
        this.additionalRespData44 = additionalRespData44;
    }

    /** 48域-附加数据-私有 */
    public String getAdditionalDataPrivate48() {
        return additionalDataPrivate48;
    }

    /** 48域-附加数据-私有 */
    public void setAdditionalDataPrivate48(String additionalDataPrivate48) {
        this.additionalDataPrivate48 = additionalDataPrivate48;
    }

    /** 49域-交易货币代码 */
    public String getCurrencyCode49() {
        return currencyCode49;
    }

    /** 49域-交易货币代码 */
    public void setCurrencyCode49(String currencyCode49) {
        this.currencyCode49 = currencyCode49;
    }

    /** 52域-个人标识码数据 */
    public String getPersonalIDCodeData52() {
        return personalIDCodeData52;
    }

    /** 52域-个人标识码数据 */
    public void setPersonalIDCodeData52(String personalIDCodeData52) {
        this.personalIDCodeData52 = personalIDCodeData52;
    }

    /** 53域-安全控制信息 */
    public String getSafeControlInfo53() {
        return safeControlInfo53;
    }

    /** 53域-安全控制信息 */
    public void setSafeControlInfo53(String safeControlInfo53) {
        this.safeControlInfo53 = safeControlInfo53;
    }

    /** 54域-附加金额 */
    public String getAdditionalAmt54() {
        return additionalAmt54;
    }

    /** 54域-附加金额 */
    public void setAdditionalAmt54(String additionalAmt54) {
        this.additionalAmt54 = additionalAmt54;
    }

    /** 55域-IC卡数据域 */
    public String getICCardDataDomain55() {
        return ICCardDataDomain55;
    }

    /** 55域-IC卡数据域 */
    public void setICCardDataDomain55(String ICCardDataDomain55) {
        this.ICCardDataDomain55 = ICCardDataDomain55;
    }

    /** 57域-自定义域 */
    public String getFld57Domain57() {
        return fld57Domain57;
    }

    /** 57域-自定义域 */
    public void setFld57Domain57(String fld57Domain57) {
        this.fld57Domain57 = fld57Domain57;
    }

    /** 58域-PBOC电子钱包/存折标准的交易信息 */
    public String getEWalletTransInfo58() {
        return eWalletTransInfo58;
    }

    /** 58域-PBOC电子钱包/存折标准的交易信息 */
    public void setEWalletTransInfo58(String eWalletTransInfo58) {
        this.eWalletTransInfo58 = eWalletTransInfo58;
    }

    /** 60域-自定义域 */
    public String getFld60Domain60() {
        return fld60Domain60;
    }

    /** 60域-自定义域 */
    public void setFld60Domain60(String fld60Domain60) {
        this.fld60Domain60 = fld60Domain60;
    }

    /** 61域-原始信息域 */
    public String getOriginalInfoDomain61() {
        return originalInfoDomain61;
    }

    /** 61域-原始信息域 */
    public void setOriginalInfoDomain61(String originalInfoDomain61) {
        this.originalInfoDomain61 = originalInfoDomain61;
    }

    /** 62域-自定义域 */
    public String getFld62Domain62() {
        return fld62Domain62;
    }

    /** 62域-自定义域 */
    public void setFld62Domain62(String fld62Domain62) {
        this.fld62Domain62 = fld62Domain62;
    }

    /** 63域-自定义域 */
    public String getFld63Domain63() {
        return fld63Domain63;
    }

    /** 63域-自定义域 */
    public void setFld63Domain63(String fld63Domain63) {
        this.fld63Domain63 = fld63Domain63;
    }

    /** 64域-MAC */
    public String getMac64() {
        return mac64;
    }

    /** 64域-MAC */
    public void setMac64(String mac64) {
        this.mac64 = mac64;
    }
}