package org.answer.common.util.excel.expand;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * <p>
 * </p>
 *
 * @author answer
 * @version 1.0
 * @date 2019-11-05
 */
@Data
public class User {
    @ExcelProperty(index = 1)
    private String userName;
    @ExcelProperty(index = 2)
    private String password;
    @ExcelProperty(index = 3)
    private Byte status;
}