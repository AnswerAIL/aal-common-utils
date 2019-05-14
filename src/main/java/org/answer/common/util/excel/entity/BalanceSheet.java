package org.answer.common.util.excel.entity;


import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Answer.AI.L
 * @date 2019-05-14
 */
@Data
@AllArgsConstructor
public class BalanceSheet {

    private String subject;
    private String initValue;
    private String finalValue;

}