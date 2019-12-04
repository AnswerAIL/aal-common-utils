package org.answer.common.util.excel.expand;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.format.NumberFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 * </p>
 *
 * @author answer
 * @version 1.0
 * @date 2019-11-05
 */
@Data
@ContentRowHeight(10)
@HeadRowHeight(20)
@ColumnWidth(25)
public class AssetData {

    // 不确定表头的转换器: EasyExcel.read(fileName, User.class, aesAnalysisEventListener).registerConverter(new MyStringConverter()).build();
    @ExcelProperty(value = {"主标题", "二级主标题", "姓名"}, converter = MyStringConverter.class)
    private String userName;

    @ColumnWidth(50)
    @ExcelProperty({"主标题", "二级主标题", "密码"})
    private String password;

    @ExcelProperty({"主标题", "二级副标题", "状态"})
    private Byte status;

    @DateTimeFormat("yyyy年MM月dd日HH时mm分ss秒")
    @ExcelProperty({"主标题", "二级副标题", "日期标题"})
    private Date date;

    @NumberFormat("#.##%")
    @ExcelProperty({"主标题", "资产金额"})
    private Double assetAmt;
}