package org.answer.common.util.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.answer.common.util.excel.entity.BalanceSheet;
import org.answer.common.util.exception.AALException;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;

import static org.answer.common.util.Constants.COMMA_SEP;

/**
 * 资产负债表 excel 解析器
 *
 * @author Answer.AI.L
 * @date 2019-05-13
 *
 *
 * TODO Answer： 针对大类中间出现解释行待解决
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BalanceAnalysis extends AnalysisEventListener {

    /**
     * 科目所在列, 多个用 {@link org.answer.common.util.Constants#COMMA_SEP} 隔开
     * */
    private String[] kms;
    /**
     * 期初值所在列, 多个用 {@link org.answer.common.util.Constants#COMMA_SEP} 隔开
     * */
    private String[] qczs;
    /**
     * 期末值所在列, 多个用 {@link org.answer.common.util.Constants#COMMA_SEP} 隔开
     * */
    private String[] qmzs;

    /**
     * 数据集
     * */
    private HashMap<String, ArrayList<BalanceSheet>> datas;
    /**
     * 状态标识
     * */
    private String[] status;

    public BalanceAnalysis(String kmCols, String qczCols, String qmzCols) {
        this.kms = kmCols.split(COMMA_SEP);
        this.qczs = qczCols.split(COMMA_SEP);
        this.qmzs = qmzCols.split(COMMA_SEP);

        if (kms.length == 0 ||
                kms.length != qczs.length ||
                kms.length != qmzs.length) {
            throw new AALException(String.format("kmCols[%s] qczCols[%s] qmzCols[%s] 's length must be equal.",
                    kmCols, qczCols, qmzCols));
        }

        this.datas = new HashMap<>();
        this.status = new String[kms.length];
    }

    @Override
    public void invoke(Object object, AnalysisContext context) {
        System.out.println("current line num：" + context.getCurrentRowNum());

        ArrayList<BalanceSheet> balanceSheets;

        ArrayList rows = (ArrayList) object;
        for (int i = 0; i < kms.length; i++) {
            String subject = rows.get(Integer.parseInt(kms[i])).toString().replace(" ", "");

            // 开始标识符
            if (subject.endsWith("：") || subject.endsWith(":")) {
                if (!datas.containsKey(subject)) {
                    datas.put(subject, new ArrayList<>());
                }

                status[i] = subject;
                continue;
            }

            // 结束标识符
            if (subject.endsWith("合计") || subject.endsWith("总计")) {
                status[i] = null;
                continue;
            }

            String key = status[i];
            if (StringUtils.isNotEmpty(key) && !"".equals(subject)) {
                String initVal = rows.get(Integer.parseInt(qczs[i])).toString();
                String finalVal = rows.get(Integer.parseInt(qmzs[i])).toString();

                BalanceSheet balanceSheet = new BalanceSheet(subject, initVal, finalVal);
                balanceSheets = datas.get(key);
                balanceSheets.add(balanceSheet);
                datas.put(key, balanceSheets);
            }

        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        this.kms = null;
        this.qczs = null;
        this.qmzs = null;
        this.status = null;
    }

}