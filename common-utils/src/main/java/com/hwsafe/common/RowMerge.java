package com.hwsafe.common;

import lombok.Data;

/**
 * @author chengql 行合并
 *
 */
@Data
public class RowMerge {
    /**
     * 开始行
     */
    private int startRowIndex;
    /**
     * 列
     */
    private int columnIndex;
    private int nextRowIndex;
    private int lastRowIndex;

    private String columnContent;

    public RowMerge(int startRowIndex, int columnIndex, String columnContent) {
        super();
        this.startRowIndex = startRowIndex;
        this.columnIndex = columnIndex;
        this.nextRowIndex = startRowIndex + 1;
        this.columnContent = columnContent;
        this.lastRowIndex = startRowIndex;
    }

    /**
     * @param 计算
     */
    public void calculate(int row) {
        if (row == nextRowIndex) {
            lastRowIndex = row;
            nextRowIndex = row + 1;
        }
    }

    public boolean isMerge() {

        return lastRowIndex > startRowIndex;

    }

}
