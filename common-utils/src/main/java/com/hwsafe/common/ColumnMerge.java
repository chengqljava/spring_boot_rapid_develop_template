package com.hwsafe.common;

import lombok.Data;

/**
 * @author chengql 列合并
 *
 */
@Data
public class ColumnMerge {
    /**
     * 开始列
     */
    private int startColumnIndex;
    /**
     * 行
     */
    private int rowIndex;
    /**
     * 下一列
     */
    private int nextColumnIndex;
    /**
     * 最后一列
     */
    private int lastColumnIndex;
    /**
     * 列内容
     */
    private String columnContent;

    public ColumnMerge(int startColumnIndex, int rowIndex,
            String columnContent) {
        super();
        this.startColumnIndex = startColumnIndex;
        this.rowIndex = rowIndex;
        this.columnContent = columnContent;
        this.nextColumnIndex = startColumnIndex + 1;
    }

    /**
     * @param 计算
     */
    public void calculate(int columnIndex) {
        if (nextColumnIndex == columnIndex) {
            lastColumnIndex = columnIndex;
            nextColumnIndex = columnIndex + 1;
        }
    }

    public boolean isMerge() {

        return lastColumnIndex > startColumnIndex;

    }

}
